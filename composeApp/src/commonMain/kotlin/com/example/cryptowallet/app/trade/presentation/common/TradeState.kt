package com.example.cryptowallet.app.trade.presentation.common

import com.example.cryptowallet.app.realtime.domain.ConnectionState
import org.jetbrains.compose.resources.StringResource

data class TradeState(
    val isLoading: Boolean = false,
    val error: StringResource? = null,
    val availableAmount: String = "",
    val amount: String = "",
    val coin: UiTradeCoinItem? = null,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED
)