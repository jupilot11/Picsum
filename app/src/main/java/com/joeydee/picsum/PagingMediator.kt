package com.joeydee.picsum

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.joeydee.picsum.db.AppDataBase
import com.joeydee.picsum.model.Person
import com.joeydee.picsum.model.RemoteKey
import com.joeydee.picsum.utils.api.PicsumDataSource
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val DEFAULT_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class PagingMediator(
    val database: AppDataBase,
    private val networkService: PicsumDataSource
) : RemoteMediator<Int, Person>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Person>): MediatorResult {
         try {

            val pageKeyData = getKeyPageData(loadType, state)
            val page = when (pageKeyData) {
                is MediatorResult.Success -> {
                    return pageKeyData
                }
                else -> {
                    pageKeyData as Int
                }
            }

             val response = networkService.getPosts(page)
             val isEndOfList = response?.isEmpty()
             database.withTransaction {
                 if (loadType == LoadType.REFRESH) {
                     database.remoteKeyDao().clearRemoteKeys()
                     database.personDao().clearAll()
                 }
                 val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                 val nextKey = if (isEndOfList == true) null else page + 1
                 val keys = response?.map {
                     RemoteKey(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                 }
                 if (keys != null) {
                     database.remoteKeyDao().insertAll(keys)
                 }
                 if (response != null) {
                     database.personDao().insertAll(response)
                 }
             }
             return MediatorResult.Success(endOfPaginationReached = isEndOfList == true)

        } catch (e: IOException) {
          return  MediatorResult.Error(e)
        } catch (e: HttpException) {
          return  MediatorResult.Error(e)
        }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, Person>): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeyDao().remoteKeysQuery(repoId)
            }
        }
    }
    private suspend fun getLastRemoteKey(state: PagingState<Int, Person>): RemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { person -> database.remoteKeyDao().remoteKeysQuery(person.id) }
    }
    private suspend fun getFirstRemoteKey(state: PagingState<Int, Person>): RemoteKey? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { person -> database.remoteKeyDao().remoteKeysQuery(person.id) }
    }

    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Person>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")

                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }
}
