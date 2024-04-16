package com.errorsmasher.passworddrive.utils

import android.content.Context
import android.content.SharedPreferences


object Prefs : BaseUtil() {
    //    Private variables should not be accessible from outside of this class
    private const val sPrefName = "appPreference"
    private lateinit var preference: SharedPreferences
    fun setAppFileVersion(version: Int, fileName: String) {
        log("setAppFileVersion() start")
        val key = PrefKeys.APP_VER + fileName
        log("Key: $key,version: $version")
        setInt(key, version)
        log("setAppFileVersion() end")
    }

    fun getAppFileVersion(fileName: String): Int {
        log("getAppFileVersion() start")
        val key = PrefKeys.APP_VER + fileName
        val value = getInt(key, 0)
        log("Key: $key,value: $value")
        log("getAppFileVersion() end")
        return value
    }

    fun getFrFileVersion(fileName: String): Int {
        log("getFrFileVersion() start")
        val key = PrefKeys.FR_VER + fileName
        val value = getInt(key, 0)
        log("Key: $key,value: $value")
        log("getFrFileVersion() end")
        return value
    }

    fun setFrVersion(version: Int, fileName: String) {
        log("setFrVersion() start")
        val key = PrefKeys.FR_VER + fileName
        log("Key: $key,version: $version")
        setInt(key, version)
        log("setFrVersion() end")
    }
    /**
     * This function will set the activityCount for the provided activity name
     * which the count this activity has started
     * @param activityName
     */
    fun setActivityCount(activityName: String) {
        log("setActivityCount() start")
        val key = "${PrefKeys.ACTIVITY_COUNT}$activityName"
        val value = getActivityCount(activityName)
        log("Key: $key")
        log("Count: ${value + 1}")
        setInt(key, value + 1)
        log("setActivityCount() end")
    }
    /**
     * This will return count of activity, which is count of how many times
     * this activity has started
     * @param activityName
     * @return Int
     */
    private fun getActivityCount(activityName: String): Int {
        log("getActivityCount() start")
        val key = "${PrefKeys.ACTIVITY_COUNT}$activityName"
        val value = getInt(key, 0)
        log("Key: $key")
        log("Count: $value")
        log("getActivityCount() end")
        return value
    }
    /**
     * This function will return boolean value depending upon whether the
     * given app version is free or not
     * @return Boolean
     */
    fun isFreeVer(): Boolean {
        val isFreeVer = getBoolean(PrefKeys.isFreeVer, true)
        log("isFreeVersion: $isFreeVer")
        return isFreeVer
    }
    /**
     * This function will set the current application version to premium
     */
    fun setPaidVersion() {
        log("setPaidVersion() start")
        setBoolean(PrefKeys.isFreeVer, false)
        log("setPaidVersion() end")
    }
    /**
     * This function will set the app version to be free.
     */
    fun setFreeVersion() {
        log("setFreeVersion() start")
        setBoolean(PrefKeys.isFreeVer, true)
        log("setFreeVersion() end")
    }
    /**
     * This function will return boolean depending upon whether the application has
     * subscribed for the premium plan or not.
     * @return Boolean
     */
    fun isPaidVer(): Boolean {
        val isPaidVer = !isFreeVer()
        log("isPaidVer: $isPaidVer")
        return isPaidVer
    }
    /**
     * This function will only initialize preference property
     * @param context : preferred to use custom application context
     * @return Nothing
     */
    fun init(context: Context) {
        if (!this::preference.isInitialized) {
            preference =
                context.getSharedPreferences(sPrefName, Context.MODE_PRIVATE)
        }
    }
    /**
     * To get a string out of sharedPreference
     * @param key
     * @param defaultValue
     */
    fun getString(key: String, defaultValue: String? = ""): String {
        val value = preference.getString(key, defaultValue).toString()
        log("key: $key value :$value")
        return value
    }
    /**
     * To get Int out of sharedPreference
     * @param key
     * @param defaultValue
     */
    fun getInt(key: String, defaultValue: Int = 0): Int {
        val value = preference.getInt(key, defaultValue)
        log("key: $key value :$value")
        return value
    }
    /**
     * To get Boolean out of sharedPreference
     * @param key
     * @param defaultValue
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        val value = preference.getBoolean(key, defaultValue)
        log("key: $key value :$value")
        return value
    }
    /**
     * To get Float out of sharedPreference
     * @param key
     * @param defaultValue
     */
    fun getFloat(key: String, defaultValue: Float = 0F): Float {
        val value = preference.getFloat(key, defaultValue)
        log("key: $key value :$value")
        return value
    }
    /**
     * To get Long out of sharedPreference
     * @param key
     * @param defaultValue
     */
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        val value = preference.getLong(key, defaultValue)
        log("key: $key value :$value")
        return value
    }
    /**
     * To get a string out of sharedPreference
     * @param key
     * @param value
     */
    fun setString(key: String, value: String? = "") {
        preference.edit().putString(key, value).apply()
        log("key: $key value :$value")
    }
    /**
     * To get Int out of sharedPreference
     * @param key
     * @param value
     */
    fun setInt(key: String, value: Int = 0) {
        preference.edit().putInt(key, value).apply()
        log("key: $key value :$value")
    }
    /**
     * To get Boolean out of sharedPreference
     * @param key
     * @param value
     */
    fun setBoolean(key: String, value: Boolean = false) {
        preference.edit().putBoolean(key, value).apply()
        log("key: $key value :$value")
    }
    /**
     * To get Float out of sharedPreference
     * @param key
     * @param value
     */
    fun setFloat(key: String, value: Float = 0F) {
        preference.edit().putFloat(key, value).apply()
        log("key: $key value :$value")
    }
    /**
     * To get Long out of sharedPreference
     * @param key
     * @param value
     */
    fun setLong(key: String, value: Long = 0L) {
        preference.edit().putLong(key, value).apply()
        log("key: $key value :$value")
    }
    /**
     * To save content of Json file downloaded from storageUtil
     * @param name
     * @param values
     */
//    fun saveAdUnit(values: String) {
//        val name = Constants.FileNames.AdUnit.name
//        setString(name, values)
//        AdIdModel.refreshAll(values)
//        log("key: $name value :$values")
//    }
//
//    fun saveOtherApps(values: String) {
//        val name = Constants.FileNames.OtherApps.name
//        setString(name, values)
//        log("key : $name, values : $values")
//    }
//    /**
//     * To getFrom pref
//     * @param fileName
//     */
//    fun getFile(fileName: Constants.FileNames): String {
//        return getString(fileName.name, "")
//    }
}