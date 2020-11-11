package com.example.brainvira_task.network


sealed class APIResult<out T: Any>{
    data class Success<out T: Any>(val data: T?): APIResult<T>()
    data class Error(val errorBody: Any?): APIResult<Nothing>()
}