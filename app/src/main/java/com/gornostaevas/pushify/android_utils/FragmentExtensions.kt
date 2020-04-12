package com.gornostaevas.pushify.android_utils

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gornostaevas.pushify.R

fun Fragment.setupToolbar(title: String, homAsUp: Boolean) {
    (activity!! as AppCompatActivity).supportActionBar!!.setTitle(title)
    (activity!! as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(homAsUp)
}

fun Fragment.setupToolbar(@StringRes id: Int, homAsUp: Boolean) {
    (activity!! as AppCompatActivity).supportActionBar!!.setTitle(id)
    (activity!! as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(homAsUp)
}