package com.example.brainvira_task.extensions

import android.view.View

fun View.GONE() {
    this.visibility = View.GONE
}

fun View.VISIBLE() {
    this.visibility = View.VISIBLE
}

fun View.INVISIBLE() {
    this.visibility = View.INVISIBLE
}

fun View.DISABLED() {
    this.isEnabled = false
}

fun View.ENABLED() {
    this.isEnabled = true
}