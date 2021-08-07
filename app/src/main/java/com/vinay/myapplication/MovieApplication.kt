package com.vinay.myapplication

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.provider.FontRequest
import com.vinay.myapplication.di.component.ApplicationComponent
import com.vinay.myapplication.di.module.ApiServiceModule
import com.vinay.myapplication.di.module.AppDatabaseModule
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject
import com.vinay.myapplication.BuildConfig
import com.vinay.myapplication.di.component.DaggerApplicationComponent
import com.vinay.myapplication.local.AppDatabase


class MovieApplication : Application() {



    val component: ApplicationComponent? by lazy {
        DaggerApplicationComponent.builder().apiServiceModule(ApiServiceModule(this)).appDatabaseModule(
            AppDatabaseModule(this)
        ).build()
    }




    @Inject
    lateinit var database: AppDatabase

    override fun onCreate() {

        component?.inject(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        super.onCreate()






//        s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));

    }


    var mInternetConnectionListener: InternetConnectionListener?=null

    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
    fun setInternetConnectionListener(listener: InternetConnectionListener) {
        mInternetConnectionListener = listener
        //    Timber.e("INTET LISTENET")
    }

    fun removeInternetConnectionListener() {
        mInternetConnectionListener = null
    }
    interface InternetConnectionListener {
        fun onInternetAvailable()
        fun onInternetUnavailable()
        fun onCacheUnavailable()
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, @NonNull message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
            Timber.log(priority,tag,t)

            if (t != null) {
                if (priority == Log.ERROR) {
                    Timber.log(priority,t)
                } else if (priority == Log.WARN) {
                    Timber.log(priority,t)
                }
            }
        }
    }


}