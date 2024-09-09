package com.example.scogo.di

import com.example.scogo.network.NetworkCall
import com.example.scogo.network.api.CoinRepository
import com.example.scogo.network.api.CoinRepositoryImplementation
import com.example.scogo.network.api.CoinsService

interface RepositoryModule {
    val coinRepository: CoinRepository
}

class RepositoryModuleImpl(
    private val networkCall: NetworkCall,
    private val coinsService: CoinsService
) : RepositoryModule {
    override val coinRepository: CoinRepository by lazy {
        CoinRepositoryImplementation(coinsService, networkCall)
    }
}