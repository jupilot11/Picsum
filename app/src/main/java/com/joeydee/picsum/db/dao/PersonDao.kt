package com.joeydee.picsum.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joeydee.picsum.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface  PersonDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<Person>)

    @Query("SELECT * FROM persons")
    fun pagingSource(): PagingSource<Int, Person>

    @Query("SELECT * FROM persons")
    fun getAll(): List<Person>


    @Query("SELECT * FROM persons")
    fun getPersons(): Flow<List<Person>>

    @Query("DELETE FROM persons")
    suspend fun clearAll()
}