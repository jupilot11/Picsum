package com.joeydee.picsum.utils.api

import com.joeydee.picsum.utils.helpers.ToastHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object ApiHelper {
    fun handleError(t: Throwable) {
        when (t) {
            is HttpException -> {
                ToastHelper.showText(t.localizedMessage)
            }
            else -> {
                ToastHelper.showText("An unexpected error has occurred")
            }
        }
    }

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): T? {
        return withContext(Dispatchers.IO) {
            try {
                val respose = apiCall.invoke()
                respose
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    handleError(e)
                    null
                }
            }
        }
    }
}
