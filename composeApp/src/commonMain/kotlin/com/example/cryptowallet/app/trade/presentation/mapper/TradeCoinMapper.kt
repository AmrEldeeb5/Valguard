package com.example.cryptowallet.app.trade.presentation.mapper

import com.example.cryptowallet.app.core.domain.coin.Coin
import com.example.cryptowallet.app.trade.presentation.common.UiTradeCoinItem


fun UiTradeCoinItem.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    iconUrl = iconUrl,
)