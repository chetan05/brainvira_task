package com.example.brainvira_task.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "SearchItemDetailsResponse")
data class SearchItemDetailsResponse(
    @PrimaryKey
    @SerializedName("id")
    var id: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("link")
    var link: String = "",
    @SerializedName("comment")
    var comment: String? = null
) : Serializable