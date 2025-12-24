package com.example.cryptowallet.app.coins.presentation

import com.example.cryptowallet.app.realtime.domain.PriceDirection

data class UiCoinListItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPositive: Boolean,
    val priceDirection: PriceDirection = PriceDirection.UNCHANGED
)
