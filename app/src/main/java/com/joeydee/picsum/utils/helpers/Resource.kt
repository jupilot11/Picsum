package com.joeydee.picsum.utils.helpers

class Resource<T>(status: Status, var data: T?, var message: String?) {
    var status: Status? = status

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?, message: String): Resource<T> {
            return Resource(Status.SUCCESS, data, message)
        }

        fun <T> error(data: T?, msg: String): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}

