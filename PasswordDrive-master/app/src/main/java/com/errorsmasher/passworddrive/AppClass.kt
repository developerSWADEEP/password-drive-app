package com.errorsmasher.passworddrive

import android.app.Application
import com.errorsmasher.passworddrive.utils.Prefs.init


class AppClass : Application() {
    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}