package com.example.scogo.network.api

import com.example.scogo.network.NetworkCall
import com.example.scogo.network.Resource
import com.example.scogo.network.api.model.CoinDetailModel
import com.example.scogo.network.api.model.CoinsModel
import kotlinx.coroutines.flow.Flow

class CoinRepositoryImplementation(
    private val coinsService: CoinsService,
    private val networkCall: NetworkCall
): CoinRepository {
    override suspend fun getCoins(): Flow<Resource<List<CoinsModel>>> {
        return networkCall.sendRequest { coinsService.getCoins() }
    }

    override suspend fun getCoinDetail(coinId: String): Flow<Resource<CoinDetailModel>> {
        return networkCall.sendRequest { coinsService.getCoinDetail(coinId) }
    }
}