package com.example.brainvira_task.Util

import android.view.View

interface StickyRecyclerListener {
    fun getHeaderPositionForItem(itemPosition: Int?): Int?

    fun getHeaderLayout(headerPosition: Int?): Int?

    fun bindHeaderData(header: View, headerPosition: Int?)

    fun isHeader(itemPosition: Int?): Boolean?
}