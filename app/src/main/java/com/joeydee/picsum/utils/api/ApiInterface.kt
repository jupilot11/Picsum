package com.joeydee.picsum.utils.api

import com.joeydee.picsum.model.Person
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/list?limit=20")
    suspend fun getPersons(@Query("page") page: Int): List<Person>

}
