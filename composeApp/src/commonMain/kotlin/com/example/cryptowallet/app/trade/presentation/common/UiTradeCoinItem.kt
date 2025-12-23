package com.example.cryptowallet.app.trade.presentation.common


data class UiTradeCoinItem(
    val id: String,
    val name: String,
    val symbol: String,
    val iconUrl: String,
    val price: Double,
)