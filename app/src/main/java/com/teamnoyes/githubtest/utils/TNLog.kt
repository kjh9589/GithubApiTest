package com.teamnoyes.githubtest.utils

import android.util.Log
import com.teamnoyes.githubtest.utils.ConstValues.LOG_TAG

object TNLog {
    fun i (msg: String) {
        Log.i(LOG_TAG, msg)
    }

    fun d (msg: String) {
        Log.d(LOG_TAG, msg)
    }

    fun e (msg: String) {
        Log.e(LOG_TAG, msg)
    }
}