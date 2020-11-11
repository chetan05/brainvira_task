package com.example.brainvira_task.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResultResponse (
    @SerializedName("data")
   var data: List<SearchQuiryResult> = listOf(),
    @SerializedName("success")
    val success : Boolean =  false
): BaseErrorResponse(), Serializable{
    data class SearchQuiryResult(
        @SerializedName("id")
        var id: String = "",
        @SerializedName("title")
        var title: String = "",
        @SerializedName("images")
        var images: List<Images> = listOf(),
    ):Serializable
    data class Images(
        @SerializedName("link")
        var link: String = "",
    ):Serializable
}