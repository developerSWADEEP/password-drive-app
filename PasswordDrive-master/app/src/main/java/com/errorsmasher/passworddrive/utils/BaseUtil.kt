package com.errorsmasher.passworddrive.utils

open class BaseUtil {
    val TAG = javaClass.simpleName.toString()
    fun log(message: String) {
        LogUtil.i(TAG, message)
    }
}