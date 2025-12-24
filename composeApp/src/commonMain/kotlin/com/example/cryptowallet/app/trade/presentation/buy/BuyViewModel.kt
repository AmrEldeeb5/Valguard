package com.example.cryptowallet.app.trade.presentation.buy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptowallet.app.coins.domain.usecase.GetCoinDetailsUseCase
import com.example.cryptowallet.app.core.domain.Result
import com.example.cryptowallet.app.core.util.formatFiat
import com.example.cryptowallet.app.core.util.toUiText
import com.example.cryptowallet.app.portfolio.domain.PortfolioRepository
import com.example.cryptowallet.app.realtime.domain.ObservePriceUpdatesUseCase
import com.example.cryptowallet.app.trade.domain.BuyCoinUseCase
import com.example.cryptowallet.app.trade.presentation.common.TradeState
import com.example.cryptowallet.app.trade.presentation.common.UiTradeCoinItem
import com.example.cryptowallet.app.trade.presentation.mapper.toCoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SCREEN_ID = "buy_screen"

class BuyViewModel(
    private val getCoinDetailsUseCase: GetCoinDetailsUseCase,
    private val portfolioRepository: PortfolioRepository,
    private val buyCoinUseCase: BuyCoinUseCase,
    private val observePriceUpdatesUseCase: ObservePriceUpdatesUseCase? = null
) : ViewModel() {

    private val tempCoinId = "1" // todo: will be removed later and replaced by parameter
    private val _amount = MutableStateFlow("")
    private val _state = MutableStateFlow(TradeState())
    val state = combine(
        _state,
        _amount,
    ) { state, amount ->
        state.copy(
            amount = amount
        )
    }.onStart {
        val balance = portfolioRepository.cashBalanceFlow().first()
        getCoinDetails(balance)
        setupRealTimeUpdates()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TradeState(isLoading = true)
    )

    init {
        // Observe connection state
        observePriceUpdatesUseCase?.let { useCase ->
            viewModelScope.launch {
                useCase.connectionState.collect { connectionState ->
                    _state.update { it.copy(connectionState = connectionState) }
                }
            }

            // Observe price updates for the specific coin
            viewModelScope.launch {
                useCase.priceUpdatesFor(tempCoinId).collect { priceUpdate ->
                    _state.update { currentState ->
                        currentState.coin?.let { coin ->
                            currentState.copy(
                                coin = coin.copy(
                                    price = priceUpdate.price,
                                    priceDirection = priceUpdate.priceDirection
                                )
                            )
                        } ?: currentState
                    }
                }
            }
        }
    }

    private fun setupRealTimeUpdates() {
        observePriceUpdatesUseCase?.let { useCase ->
            viewModelScope.launch {
                useCase.start()
                useCase.subscribeScreen(SCREEN_ID, listOf(tempCoinId))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        observePriceUpdatesUseCase?.let { useCase ->
            viewModelScope.launch {
                useCase.unsubscribeScreen(SCREEN_ID)
            }
        }
    }

    private suspend fun getCoinDetails(balance: Double) {
        when (val coinResponse = getCoinDetailsUseCase.execute(tempCoinId)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        coin = UiTradeCoinItem(
                            id = coinResponse.data.coin.id,
                            name = coinResponse.data.coin.name,
                            symbol = coinResponse.data.coin.symbol,
                            iconUrl = coinResponse.data.coin.iconUrl,
                            price = coinResponse.data.price,
                        ),
                        availableAmount = "Available: ${formatFiat(balance)}"
                    )
                }
            }

            is Result.Failure -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = coinResponse.error.toUiText()
                    )
                }
            }
        }
    }

    fun onAmountChanged(amount: String) {
        _amount.value = amount
    }

    fun onBuyClicked() {
        val tradeCoin = state.value.coin ?: return
        viewModelScope.launch {
            val buyCoinResponse = buyCoinUseCase.buyCoin(
                coin = tradeCoin.toCoin(),
                amountInFiat = _amount.value.toDouble(),
                price = tradeCoin.price,
            )

            when(buyCoinResponse) {
                is Result.Success -> {
                    // TODO: Navigate to next screen with event
                }
                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = buyCoinResponse.error.toUiText(),
                        )
                    }
                }
            }
        }

    }
}