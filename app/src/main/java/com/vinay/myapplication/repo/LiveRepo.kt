package com.vinay.myapplication.repo

import android.app.Application
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinay.myapplication.base.BaseRepo
import com.vinay.myapplication.data.remote.model.MovieModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList


class LiveRepo(application: Application) : BaseRepo(application) {

    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentDate = sdf.format(Date())


    var mutableLiveData: MutableLiveData<String> = MutableLiveData()
    var previewData: MutableLiveData<MovieModel> = MutableLiveData()


    fun getLivePrepData(topRatedMoviesPage : Int) {

        apiService.getLivePrepData(
            topRatedMoviesPage
        )
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                previewData.postValue(it)
//                print("message_list : "+ listOptions);

            }, {

                it.printStackTrace()
            })
    }
    fun getPrepStatus() = previewData



}