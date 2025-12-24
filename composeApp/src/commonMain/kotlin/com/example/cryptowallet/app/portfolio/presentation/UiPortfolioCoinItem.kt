package com.example.cryptowallet.app.portfolio.presentation

import com.example.cryptowallet.app.realtime.domain.PriceDirection


data class UiPortfolioCoinItem(
    val id: String,
    val name: String,
    val iconUrl: String,
    val amountInUnitText: String,
    val amountInFiatText: String,
    val performancePercentText: String,
    val isPositive: Boolean,
    val priceDirection: PriceDirection = PriceDirection.UNCHANGED
)