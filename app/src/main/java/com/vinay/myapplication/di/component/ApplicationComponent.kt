package com.vinay.myapplication.di.component

import com.vinay.myapplication.MovieApplication
import com.vinay.myapplication.base.BaseActivity
import com.vinay.myapplication.base.BaseRepo
import com.vinay.myapplication.di.module.ApiServiceModule
import com.vinay.myapplication.di.module.AppDatabaseModule
import dagger.Component

@Component(modules = arrayOf(ApiServiceModule::class, AppDatabaseModule::class))
interface ApplicationComponent {
    fun inject(application: MovieApplication)
    fun inject(baseActivity: BaseActivity)
    fun inject(baseRepo: BaseRepo)
}