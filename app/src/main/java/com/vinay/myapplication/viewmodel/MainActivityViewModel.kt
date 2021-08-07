package com.vinay.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.vinay.myapplication.repo.LiveRepo

import java.util.ArrayList

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val searchQueryChatMutableLiveData  = MutableLiveData<String>()
    var searchStatus  = false

    var topRatedMoviesPage = 1
    var topRatedMoviesMaxPage = 1
    //  val  chatRepository:ChatRepository = ChatRepository(application)
    val  chatListRepository = LiveRepo(application)

    var botId :Int? = null
    var cid :Int? = null
    var next :Int? = null

    fun getLivePrepData(topRatedMoviesPage : Int) =chatListRepository.getLivePrepData(topRatedMoviesPage)


    fun getPrepStatus()=chatListRepository.getPrepStatus()

}