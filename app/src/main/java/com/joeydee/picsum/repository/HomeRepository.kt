package com.joeydee.picsum.repository

import androidx.paging.PagingData
import com.joeydee.picsum.model.Person
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getPersons(): List<Person>?

    fun getData() : Flow<PagingData<Person>>

    fun getResults(): Flow<List<Person>>

}
