package com.example.brainvira_task.repositories

import com.example.brainvira_task.network.APIResult
import com.example.brainvira_task.network.APIService
import com.example.brainvira_task.model.*

class MobsterRepository(val api: APIService) : BaseRepository(), ApiDataService {
    override suspend fun getSearchResult(idToken: String, query: String): APIResult<SearchResultResponse> =
        getAPIResult(safeApiCall { api.getSearchResult(idToken, query) })

    override suspend fun getRate(): APIResult<RatesResponse> =
        getAPIResult(safeApiCall { api.getRatesResult() })
}
