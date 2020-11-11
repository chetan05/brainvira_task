package com.example.brainvira_task.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.brainvira_task.model.SearchItemDetailsResponse
import com.example.brainvira_task.mvvm.BaseViewModel
import com.example.brainvira_task.repositories.local.LocalRepository
import com.example.brainvira_task.view.SearchItemDetails
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

open class SearchItemDetailsModel (
):BaseViewModel(){

    var liveSearchItemDetailsResponse = SearchItemDetailsResponse()
    val searchItemDetailsResponse: SearchItemDetailsResponse
        get() = liveSearchItemDetailsResponse

      fun loadDataFromDB() {
        if (SearchItemDetails.roomDB.isOpen) {
            try {
                viewModelScope.launch {
                    LocalRepository.getSearchItemDetailsResponse.collect {
                       liveSearchItemDetailsResponse = it
                    }
                }

            } catch (e: Exception) {
                // not handling
            }
        }
    }

}