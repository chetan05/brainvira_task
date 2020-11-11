package com.example.brainvira_task.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.brainvira_task.R
import com.example.brainvira_task.Util.SEARCH_ITEM_DETAILS
import com.example.brainvira_task.model.SearchItemDetailsResponse
import com.example.brainvira_task.repositories.local.LocalRepository
import com.example.brainvira_task.repositories.local.RoomDB
import kotlinx.android.synthetic.main.activity_search_deatils.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.brainvira_task.viewmodel.SearchItemDetailsModel
import org.koin.android.viewmodel.ext.viewModel

class SearchItemDetails : AppCompatActivity(), View.OnClickListener {
    private val searchItemDetailsModel: SearchItemDetailsModel by viewModel()

    companion object {
        lateinit var roomDB: RoomDB
        fun isRoomInitialized() = ::roomDB.isInitialized
    }

    private var searchItemDetailsResponse: SearchItemDetailsResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_deatils)
        if (intent.hasExtra(SEARCH_ITEM_DETAILS)) {
            searchItemDetailsResponse =
                intent.getSerializableExtra(SEARCH_ITEM_DETAILS) as SearchItemDetailsResponse
            searchItemDetailsResponse?.let {
                txtTitle.text = it.title
                Glide.with(this).load(it.link).into(itemImageView)
            }
            initDB()
        }
        imgBackTitleBar.setOnClickListener(this)
        llSubmit.setOnClickListener(this)
        searchItemDetailsModel.loadDataFromDB()
        addObserver()
    }

    private fun addObserver() {
        searchItemDetailsModel.searchItemDetailsResponse?.let {
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, R.anim.right_to_left)
    }

    override fun onClick(v: View?) {
        when (v) {
            imgBackTitleBar -> onBackPressed()
            llSubmit -> {
                if (roomDB.isOpen) {
                    val comment= editText.text.toString()
                    searchItemDetailsResponse = searchItemDetailsResponse?.let {
                        SearchItemDetailsResponse(
                            it.id,it.title ,it.link,comment)
                    }
                    GlobalScope.launch { searchItemDetailsResponse?.let {
                        LocalRepository.addSearchItemDetailsResponse(
                            it
                        )
                    } }
                } else {
                }
            }

        }
    }
    private fun initDB() {
        roomDB = RoomDB.getDatabase(this)
        isRoomInitialized()
    }


}