package com.example.brainvira_task.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainvira_task.R
import com.example.brainvira_task.Util.SEARCH_ITEM_DETAILS
import com.example.brainvira_task.model.SearchItemDetailsResponse
import com.example.brainvira_task.model.SearchResultResponse
import com.example.brainvira_task.viewmodel.Fail
import com.example.brainvira_task.viewmodel.SearchViewModel
import com.mobiquity.mobsterapp.dev.GeneralDialog
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.viewModel
import kotlin.coroutines.CoroutineContext


class SearchActivity(override val coroutineContext: CoroutineContext = Dispatchers.Main) :
    AppCompatActivity(), CoroutineScope {
    private val searchViewModel: SearchViewModel by viewModel()
    var searchDataList: MutableList<SearchResultResponse.SearchQuiryResult> = arrayListOf()

    lateinit var searchResultResponse: SearchResultResponse
    private lateinit var itemsAdapter: ItemsAdapter
    private lateinit var ft: FragmentManager

    companion object {
        const val API_ERROR_DIALOG_TAG = "API_ERROR_DIALOG_TAG"
        const val REQUEST_DETAILS_RESULT = 1500

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        addObserver()
        initView()
        setupSearchToolbar()
        ft = supportFragmentManager
    }

    override fun onResume() {
        super.onResume()
        //Empty Method
    }

    private fun initView() {
        with(recyclerCart) {
            recyclerCart.layoutManager = GridLayoutManager(context, 4, RecyclerView.VERTICAL, false)

            itemsAdapter = ItemsAdapter(this@SearchActivity)
            adapter = itemsAdapter
        }
        itemsAdapter.onItemClick = {
            it?.let {
                navigateToSearchItemDetailsScreen(it)
            }

        }
    }

    private fun addObserver() {
        searchViewModel.showLoading.observe(this, Observer {
            if (it) progress.visibility = View.VISIBLE
            else progress.visibility = View.GONE
        })
        searchViewModel.searchResponse.observe(this, itemDataObserver)
        searchViewModel.searchResponseSate.observe(this, Observer { state ->
            when (state) {
                Fail -> {
                    GeneralDialog.newInstance(
                        this.getString(R.string.error_happened),
                        this.getString(R.string.no_connection), this.getString(R.string.ok), ""
                    ).show(
                        ft,
                        API_ERROR_DIALOG_TAG
                    )
                }

            }
        })
    }

    private val itemDataObserver = Observer<SearchResultResponse> { searchResultResponse ->
        searchResultResponse?.let {
            if (searchResultResponse.success) {
                searchResultResponse.data?.let { itemList ->
                    searchDataList = itemList.toMutableList()
                }
                itemsAdapter.updateItem(searchDataList)
            }
        }
    }

    private fun setupSearchToolbar() {
        imgClose.setOnClickListener {
            searchEditText.text = null
            imgClose.visibility = View.GONE
        }


        searchEditText.apply {
            this.addTextChangedListener(object : TextWatcher {
                private var searchFor = ""
                override fun afterTextChanged(s: Editable) {
                    //Empty Method
                }

                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                    //Empty Method
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    if (s.isEmpty()) imgClose.visibility = View.GONE else imgClose.visibility =
                        View.VISIBLE
                    val searchText = s.toString().trim()
                    if (searchText == searchFor) return

                    searchFor = searchText

                    launch {
                        delay(250)  //debounce timeOut
                        if (searchText != searchFor)
                            return@launch

                        determineSearchCriteria()
                    }
                }
            })
            onEditorAction(EditorInfo.IME_ACTION_SEARCH) {
                determineSearchCriteria()
                hideKeyBoardOnView(this)
                this.clearFocus()
            }
        }
    }

    private fun determineSearchCriteria() {
        val query = searchEditText.text.toString()
        if (query.isEmpty()) {
            // we can put here empty view
        } else {
            searchViewModel.loadRates()
            imgClose.visibility = View.VISIBLE
        }

    }

    fun TextView.onEditorAction(action: Int = EditorInfo.IME_ACTION_DONE, cb: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                action -> {
                    cb.invoke()
                    true
                }
                else -> false
            }
        }
    }

    fun hideKeyBoardOnView(v: View) {
        val imm = v.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun navigateToSearchItemDetailsScreen(searchItemDetailsResponse: SearchItemDetailsResponse) {
        val intent = Intent(this, SearchItemDetails::class.java)
        intent.putExtra(SEARCH_ITEM_DETAILS, searchItemDetailsResponse)
        startActivityForResult(intent, REQUEST_DETAILS_RESULT)
        overridePendingTransition(R.anim.left_to_right, R.anim.no_change)
    }

}