package com.example.brainvira_task.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brainvira_task.R
import com.example.brainvira_task.model.CustomerOrderDataList
import com.example.brainvira_task.model.RatesResponse
import com.example.brainvira_task.viewmodel.Fail
import com.example.brainvira_task.viewmodel.SearchViewModel
import com.mobiquity.mobsterapp.dev.GeneralDialog
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.viewModel
import kotlin.coroutines.CoroutineContext

class RatesActivity(override val coroutineContext: CoroutineContext = Dispatchers.Main) :
    AppCompatActivity(), CoroutineScope {

    private val customerOrderDataList = ArrayList<CustomerOrderDataList>()
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var ratesItemAdapter: RatesUpdatedAdapter
    private lateinit var ft: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)
        searchViewModel.loadRates()
        initView()
        addObserver()


        ft = supportFragmentManager
    }

    override fun onResume() {
        super.onResume()
        //Empty Method
    }
    private fun initView() {
        with(recyclerCart) {
            recyclerCart.layoutManager =   LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            ratesItemAdapter = RatesUpdatedAdapter(this@RatesActivity)
            adapter = ratesItemAdapter
        }
    }

    private fun addObserver() {
        searchViewModel.showLoading.observe(this, Observer {
            if (it) progress.visibility = View.VISIBLE
            else progress.visibility = View.GONE
        })
        searchViewModel.ratesResponse.observe(this, itemDataObserver)
        searchViewModel.searchResponseSate.observe(this, Observer { state ->
            when (state) {
                Fail -> {
                    GeneralDialog.newInstance(
                        this.getString(R.string.error_happened),
                        this.getString(R.string.no_connection), this.getString(R.string.ok), ""
                    ).show(
                        ft,
                        SearchActivity.API_ERROR_DIALOG_TAG
                    )
                }

            }
        })
    }

    private val itemDataObserver = Observer<RatesResponse> { ratesResponse ->
        ratesResponse.let {
           it.rates?.let { dates->
               dates.keys.forEach { headerDate ->
                   val headerModel =
                       CustomerOrderDataList(headerDate, null, RatesUpdatedAdapter.ItemType.TYPE_DATE_HEADER)
                   customerOrderDataList.add(headerModel)

                       val model = CustomerOrderDataList(
                           headerDate,
                           dates.getValue(headerDate),
                           RatesUpdatedAdapter.ItemType.TYPE_RATES
                       )
                       customerOrderDataList.add(model)
               }
               ratesItemAdapter?.submitList(customerOrderDataList)
           }

        }

    }
}