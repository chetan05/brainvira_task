package com.example.brainvira_task.repositories


import com.example.brainvira_task.model.RatesResponse
import com.example.brainvira_task.model.SearchResultResponse
import com.example.brainvira_task.network.APIResult

interface ApiDataService {
    suspend fun getSearchResult(idToken: String,query:String): APIResult<SearchResultResponse>
    suspend fun getRate():APIResult<RatesResponse>
}
