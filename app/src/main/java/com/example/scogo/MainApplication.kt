package com.example.scogo

import android.app.Application
import com.example.scogo.di.NetworkModule
import com.example.scogo.di.NetworkModuleImpl
import com.example.scogo.di.RepositoryModule
import com.example.scogo.di.RepositoryModuleImpl
import com.example.scogo.di.RetrofitServiceModule
import com.example.scogo.di.RetrofitServiceModuleImpl

class MainApplication: Application() {
    companion object{
        const val TAG = "MainApplication"
        lateinit var networkModule: NetworkModule
            private set
        lateinit var repositoryModule: RepositoryModule
            private set
        lateinit var retrofitServiceModule: RetrofitServiceModule
            private set
        var BASE_URL: String = BuildConfig.API_BASE_URL
    }

    override fun onCreate() {
        super.onCreate()
        networkModule = NetworkModuleImpl()
        retrofitServiceModule = RetrofitServiceModuleImpl(
            networkModule.retrofit
        )
        repositoryModule = RepositoryModuleImpl(
            networkCall = networkModule.networkCall,
            coinsService = retrofitServiceModule.coinsService
        )
    }
}