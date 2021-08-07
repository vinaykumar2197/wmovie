package com.vinay.myapplication.local

import android.content.Context
import java.util.*


open class UserSharedPrefs(mContext: Context) {


    val CURRENT_DATE = "current_date"

    var preff = mContext?.getSharedPreferences(KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE)
    var prefEditor = preff.edit()

    companion object {
        private var INSTANCE: UserSharedPrefs? = null
        val KEY_SHARED_PREF_NAME = "movie_shared_pref"

        fun getSharedPref(context: Context): UserSharedPrefs {
            if (INSTANCE == null) {
                INSTANCE = UserSharedPrefs(context)
            }
            return INSTANCE as UserSharedPrefs
        }
    }


    fun getCurrentDate(): String? {
        return preff.getString(CURRENT_DATE, "")
    }


    fun setCurrentDate(currentDate: String?) {
        prefEditor.putString(CURRENT_DATE, currentDate)
        prefEditor.commit()
    }



}