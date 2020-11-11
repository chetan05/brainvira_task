package com.example.brainvira_task.view

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.brainvira_task.model.SearchResultResponse
import com.example.brainvira_task.viewmodel.SearchViewModel
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import kotlin.jvm.Throws

@RunWith(RobolectricTestRunner::class)
class SearchActivityTest {

    private lateinit var searchActivity: SearchActivity
    private lateinit var searchResultResponse: SearchResultResponse
    private lateinit var searchViewModel: SearchViewModel
    lateinit var context: Context

    @Before
    @Throws(Exception::class)
    fun setUp() {
        context =
            InstrumentationRegistry.getInstrumentation().context
        searchActivity = Robolectric.buildActivity(SearchActivity::class.java)
            .create().resume()
            .get()
        searchResultResponse = Gson().fromJson(
            TestUtil.getTestResourceString("mock_responses/search_result_responce.json"),
            SearchResultResponse::class.java
        )
       // searchViewModel = searchActivity.withViewModel({ SearchViewModel(ApiDataService) })
    }
    @Test
    fun testNotNull() {
        Assert.assertNotNull(searchActivity)
        Assert.assertNotNull(searchResultResponse)
    }
}