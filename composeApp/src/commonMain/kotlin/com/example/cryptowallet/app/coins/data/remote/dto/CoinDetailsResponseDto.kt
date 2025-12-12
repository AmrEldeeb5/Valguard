package com.example.cryptowallet.app.coins.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoinDetailsResponseDto(
    val data: CoinDetailsDataDto
)

@Serializable
data class CoinDetailsDataDto(
    val coin: List<CoinDetailsDto>
)

@Serializable
data class CoinDetailsDto(
    val uuid: String,
    val symbol: String,
    val name: String,
    val iconUrl: String,
    val price: Double,
    val rank: Int,
    val change: Double
)
