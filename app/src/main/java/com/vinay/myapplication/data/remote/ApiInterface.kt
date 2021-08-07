package com.vinay.myapplication.data.remote


import com.vinay.myapplication.data.remote.model.MovieModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @GET("/3/search/movie?api_key=6753d9119b9627493ae129f3c3c99151&query=superman")
    fun getLivePrepData(
        @Query("page") pageIndex : Int
    ) : Observable<MovieModel>


}