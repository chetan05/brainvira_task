package com.example.brainvira_task.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.brainvira_task.Util.AUTHORIZATION
import com.example.brainvira_task.model.RatesResponse
import com.example.brainvira_task.model.SearchResultResponse
import com.example.brainvira_task.mvvm.BaseViewModel
import com.example.brainvira_task.network.APIResult
import com.example.brainvira_task.repositories.ApiDataService
import kotlinx.coroutines.launch

open class SearchViewModel (
    private val repo: ApiDataService
) : BaseViewModel(){

    val liveSearchResponse = MutableLiveData<SearchResultResponse>()
    val searchResponse: LiveData<SearchResultResponse>
        get() = liveSearchResponse

    private var _searchResponseState = MutableLiveData<ViewModelEvent>().apply {
        value = Uninitialized
    }
    val searchResponseSate: LiveData<ViewModelEvent> get() = _searchResponseState


    val liveRatesResponse = MutableLiveData<RatesResponse>()
    val ratesResponse: LiveData<RatesResponse>
        get() = liveRatesResponse


    fun loadReservation(query: String) {
        showLoading.value = true
        val idToken = AUTHORIZATION
        viewModelScope.launch {
            val result = idToken?.let {repo.getSearchResult(it,query) }
             showLoading.postValue(false)
            when (result) {
                is APIResult.Success -> {
                    liveSearchResponse.value = result.data
                    _searchResponseState.value = Success
                }
                is APIResult.Error -> {
                    _searchResponseState.value = Fail
                }
            }
        }
    }

    fun loadRates(){
        showLoading.value = true
        viewModelScope.launch {
            val result = repo.getRate()
            showLoading.postValue(false)
            when (result) {
                is APIResult.Success -> {
                    liveRatesResponse.value = result.data
                    _searchResponseState.value = Success
                }
                is APIResult.Error -> {
                    _searchResponseState.value = Fail
                }
            }
        }
    }




}