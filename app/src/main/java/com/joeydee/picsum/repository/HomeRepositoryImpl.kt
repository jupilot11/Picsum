package com.joeydee.picsum.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.joeydee.picsum.PagingMediator
import com.joeydee.picsum.db.AppDataBase
import com.joeydee.picsum.model.Person
import com.joeydee.picsum.utils.api.PicsumDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val dataSource: PicsumDataSource,
    val appDataBase: AppDataBase
) : HomeRepository {

    override suspend fun getPersons(): List<Person>? {
        return dataSource.getPosts(1);
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getData(): Flow<PagingData<Person>> {

        val pagingSourceFactory = { appDataBase.personDao().pagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = PagingMediator(appDataBase, dataSource)
        ).flow
    }

    override fun getResults(): Flow<List<Person>> {
        return appDataBase.personDao().getPersons()
    }

}
