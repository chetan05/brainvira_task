package com.example.brainvira_task.network
import com.example.brainvira_task.model.RatesResponse
import com.example.brainvira_task.model.SearchResultResponse
import retrofit2.Response
import retrofit2.http.Query

interface APIService {
    @retrofit2.http.GET("search/1")
    suspend fun getSearchResult(
        @retrofit2.http.Header("Authorization") token: String,
        @Query("q") query: String
    ): Response<SearchResultResponse>


    @retrofit2.http.GET("history?start_at=2018-01-01&end_at=2018-09-01&base=USD")
    suspend fun getRatesResult(
    ): Response<RatesResponse>
}
