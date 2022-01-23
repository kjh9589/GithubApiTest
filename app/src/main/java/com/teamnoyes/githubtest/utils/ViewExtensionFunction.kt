package com.teamnoyes.githubtest.utils

import android.view.View

fun View.setOnSingleClickListener(listener: View.OnClickListener) {
    setOnClickListener(OnSingleClickListener(listener))
}

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}