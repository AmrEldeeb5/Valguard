package com.example.cryptowallet.app.trade.presentation.buy

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptowallet.app.trade.presentation.common.TradeScreen
import com.example.cryptowallet.app.trade.presentation.common.TradeType
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BuyScreen(
    coinId: String,
    navigateToPortfolio: () -> Unit,
) {
    val viewModel = koinViewModel<BuyViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    //TODO: handle coinId

    TradeScreen(
        state = state,
        tradeType = TradeType.BUY,
        onAmountChange = viewModel::onAmountChanged,
        onSubmitClicked = viewModel::onBuyClicked
    )
}