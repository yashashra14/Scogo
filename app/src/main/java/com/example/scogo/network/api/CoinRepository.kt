package com.example.scogo.network.api

import com.example.scogo.network.Resource
import com.example.scogo.network.api.model.CoinDetailModel
import com.example.scogo.network.api.model.CoinsModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoins(): Flow<Resource<List<CoinsModel>>>
    suspend fun getCoinDetail(coinId: String): Flow<Resource<CoinDetailModel>>
}