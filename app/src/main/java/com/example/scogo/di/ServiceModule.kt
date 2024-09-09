package com.example.scogo.di

import com.example.scogo.network.api.CoinsService
import retrofit2.Retrofit

interface RetrofitServiceModule {
    val coinsService: CoinsService
}

class RetrofitServiceModuleImpl(
    private val retrofit: Retrofit
): RetrofitServiceModule {
    override val coinsService: CoinsService by lazy {
        retrofit.create(CoinsService::class.java)
    }
}