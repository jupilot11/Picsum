package com.joeydee.picsum.utils.api

import com.joeydee.picsum.utils.api.ApiHelper.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PicsumDataSource @Inject constructor(val api: ApiInterface) {

    suspend fun getPosts(page: Int) = withContext(Dispatchers.IO) {
        safeApiCall {
            api.getPersons(page)
        }
    }
}
