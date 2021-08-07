package com.vinay.myapplication.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.vinay.myapplication.MovieApplication
import com.vinay.myapplication.data.remote.ApiInterface
import com.vinay.myapplication.local.AppDatabase
import com.vinay.myapplication.local.UserSharedPrefs
import javax.inject.Inject

open class BaseRepo {

    @Inject
    lateinit var apiService: ApiInterface

    @Inject
    lateinit var userSharedPrefs: UserSharedPrefs


    constructor(application: Application) {
        (application as MovieApplication).component?.inject(this)
    }

    var mutableStates = MutableLiveData<STATE>()
    var apiMessage = "Network Error..."
    var apiData=""


    @Inject lateinit var appDatabase: AppDatabase

    enum class STATE {
        SUCCESS,
        FAILURE,
        NEUTRAL
    }


}