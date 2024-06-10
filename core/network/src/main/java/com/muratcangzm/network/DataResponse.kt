package com.muratcangzm.network

class DataResponse<out T>
    (val status:Status, val data:T?, val message:String? = null) {


    enum class Status{
        SUCCESS, LOADING, ERROR
    }

    companion object{

        fun <T> success(data:T?) : DataResponse<T>{
            return DataResponse(status = Status.SUCCESS, data = data)
        }
        fun<T> loading():DataResponse<T>{
            return DataResponse(status = Status.LOADING, data = null)
        }
        fun<T> error(string: String?): DataResponse<T>{
            return DataResponse(status = Status.ERROR, data = null, message = string)
        }

    }

}