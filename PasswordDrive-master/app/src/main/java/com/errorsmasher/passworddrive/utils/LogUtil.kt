package com.errorsmasher.passworddrive.utils

import android.util.Log

object LogUtil {
    fun i(tag: String, message: String) {
        if (MyDebugUtil.isLogEnabled()) {
            Log.i(tag, message)
        }
    }
}