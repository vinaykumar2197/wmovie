package com.vinay.myapplication.di.module

import android.content.Context
import android.content.SharedPreferences
import com.vinay.myapplication.local.AppDatabase
import com.vinay.myapplication.local.UserSharedPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDatabaseModule(val context: Context) {
    @Provides
    fun appDatabase(context: Context): AppDatabase = AppDatabase.getDatabase(context)
    @Provides
    fun userSharedPref(context: Context): UserSharedPrefs = UserSharedPrefs.getSharedPref(context)
}