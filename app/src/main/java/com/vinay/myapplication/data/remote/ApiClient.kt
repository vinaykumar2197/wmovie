//package com.ufaber.fluentlife.data.remote
//
//import com.ufaber.fluentlife.data.remote.ApiConstants.BASE_URL
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//
//
//object ApiClient {
//
//    private var retrofit: Retrofit? = null
//
//    val client: Retrofit?
//        get() {
//            if (retrofit == null) {
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build()
//            }
//            return retrofit
//        }
//}