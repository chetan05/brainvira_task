package com.example.brainvira_task.model

import com.google.gson.annotations.SerializedName

open class BaseErrorResponse {
    @SerializedName("code")
    var code: Int = 0
    @SerializedName("message")
    var message: String? = null
    @SerializedName("error")
    var error: String? = null
}