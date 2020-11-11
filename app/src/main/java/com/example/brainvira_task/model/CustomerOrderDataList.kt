package com.example.brainvira_task.model

import java.io.Serializable

data class CustomerOrderDataList(
    var date: String,
    var item: RatesResponse.data?,
    var type: Int
) : Serializable