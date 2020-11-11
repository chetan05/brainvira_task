package com.example.brainvira_task.di

import com.example.brainvira_task.network.API
import com.example.brainvira_task.repositories.ApiDataService
import com.example.brainvira_task.repositories.MobsterRepository
import com.example.brainvira_task.rx.ApplicationSchedulerProvider
import com.example.brainvira_task.rx.SchedulerProvider
import com.example.brainvira_task.viewmodel.SearchItemDetailsModel
import com.example.brainvira_task.viewmodel.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { SearchViewModel(get() as ApiDataService) }

    viewModel { SearchItemDetailsModel() }

    single { MobsterRepository(get()) as ApiDataService }

    single<SchedulerProvider> { ApplicationSchedulerProvider() }

    single { API.apiServices }

}
val app = listOf(appModule)