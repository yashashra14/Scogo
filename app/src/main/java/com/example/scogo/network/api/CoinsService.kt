package com.example.scogo.network.api

import com.example.scogo.network.api.model.CoinDetailModel
import com.example.scogo.network.api.model.CoinsModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinsService {
    @GET("coins")
    suspend fun getCoins(): List<CoinsModel>

    @GET("tickers/{coinId}")
    suspend fun getCoinDetail( @Path("coinId") coinId: String): CoinDetailModel
}