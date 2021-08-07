package com.vinay.myapplication.di.module

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vinay.myapplication.MovieApplication
import com.vinay.myapplication.data.remote.ApiConstants
import com.vinay.myapplication.data.remote.ApiInterface
import com.vinay.myapplication.data.remote.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
class ApiServiceModule(val application: MovieApplication) {

    @Provides
    fun getContext(): Context = application

    @Provides
    fun apiService(retofit: Retrofit): ApiInterface {
        return retofit.create(ApiInterface::class.java)
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(ApiConstants.BASE_URL)
            .build()
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
//        gsonBuilder.registerTypeAdapter(DateTime::class.java, DateTimeConverter())
        return gsonBuilder.create()
    }

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(object : NetworkConnectionInterceptor() {
                override fun isInternetAvailable(): Boolean {
                    if (application.mInternetConnectionListener != null)
                        application.mInternetConnectionListener!!.onInternetAvailable()
                    return application.isInternetAvailable()
                }

                override fun onInternetUnavailable() {
                    Log.e("MODULE ", "Not connected to internet")
                    if (application.mInternetConnectionListener != null)
                        application.mInternetConnectionListener!!.onInternetUnavailable()
                }

                override fun onCacheUnavailable() {
                    Log.e("MODULE ", "Catche Not available")
                }

            })
            .readTimeout(60, TimeUnit.SECONDS)
//            .cache(cache)
            .build()
    }

    @Provides
    fun loggingInterceptor(logger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(logger)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun getLogger(): HttpLoggingInterceptor.Logger {
        val logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        }
        return logger
    }
}