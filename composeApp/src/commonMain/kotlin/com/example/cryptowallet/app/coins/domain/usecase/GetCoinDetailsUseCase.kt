package com.example.cryptowallet.app.coins.domain.usecase

import com.example.cryptowallet.app.coins.domain.api.CoinsRemoteDataSource
import com.example.cryptowallet.app.coins.domain.model.CoinModel
import com.example.cryptowallet.app.core.domain.DataError
import com.example.cryptowallet.app.core.domain.Result
import com.example.cryptowallet.app.core.domain.map
import com.example.cryptowallet.app.mapper.toCoinModel

class GetCoinDetailsUseCase(
    private val client: CoinsRemoteDataSource,
) {

    suspend fun execute(coinId: String): Result<CoinModel, DataError.Remote> {
        return client.getCoinById(coinId).map { dto ->
            dto.data.coin.first().toCoinModel()
        }
    }
}