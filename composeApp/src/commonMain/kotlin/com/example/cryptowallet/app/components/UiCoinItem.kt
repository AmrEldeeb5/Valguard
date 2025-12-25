package com.example.cryptowallet.app.components

import com.example.cryptowallet.app.realtime.domain.PriceDirection

data class UiCoinItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val formattedPrice: String,
    val formattedChange: String,
    val isPositive: Boolean,
    val priceDirection: PriceDirection,
    val holdingsAmount: String? = null,
    val holdingsValue: String? = null,
    val marketCap: String? = null,
    val volume24h: String? = null,
    val isInWatchlist: Boolean = false
) {
    fun hasHoldings(): Boolean = holdingsAmount != null && holdingsValue != null
}
