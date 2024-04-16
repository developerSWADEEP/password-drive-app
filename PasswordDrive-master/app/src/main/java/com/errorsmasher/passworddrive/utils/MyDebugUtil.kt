package com.errorsmasher.passworddrive.utils

import com.errorsmasher.passworddrive.BuildConfig

object MyDebugUtil {
    var isDebuggable: Boolean = BuildConfig.DEBUG
    fun isLogEnabled(): Boolean = isDebugE()
    fun isDebugE(): Boolean = isDebuggable
}