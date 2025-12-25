package com.example.cryptowallet.app.onboarding.data

import com.example.cryptowallet.app.onboarding.presentation.OnboardingState
import com.example.cryptowallet.app.watchlist.domain.WatchlistRepository
import kotlinx.coroutines.flow.first

class OnboardingRepository(
    private val watchlistRepository: WatchlistRepository
) {
    // In-memory storage for MVP
    // TODO: Replace with DataStore for production
    private var savedCurrentStep: Int = 0
    private var savedSelectedCoins: Set<String> = emptySet()
    private var savedNotificationsEnabled: Boolean = false
    private var onboardingCompleted: Boolean = false
    
    suspend fun getOnboardingState(): OnboardingState {
        return OnboardingState(
            currentStep = savedCurrentStep,
            selectedCoins = savedSelectedCoins,
            notificationsEnabled = savedNotificationsEnabled,
            isLoading = false
        )
    }
    
    suspend fun saveOnboardingState(state: OnboardingState) {
        savedCurrentStep = state.currentStep
        savedSelectedCoins = state.selectedCoins
        savedNotificationsEnabled = state.notificationsEnabled
    }
    
    suspend fun setOnboardingCompleted(completed: Boolean) {
        onboardingCompleted = completed
    }
    
    suspend fun isOnboardingCompleted(): Boolean {
        return onboardingCompleted
    }
    
    suspend fun saveSelectedCoinsToWatchlist(coins: Set<String>) {
        // Map coin symbols to coin IDs (lowercase for API compatibility)
        val coinIdMap = mapOf(
            "BTC" to "bitcoin",
            "ETH" to "ethereum",
            "BNB" to "binancecoin",
            "SOL" to "solana",
            "ADA" to "cardano",
            "XRP" to "ripple"
        )
        
        coins.forEach { symbol ->
            val coinId = coinIdMap[symbol] ?: symbol.lowercase()
            try {
                watchlistRepository.addToWatchlist(coinId)
            } catch (e: Exception) {
                // Continue even if one fails
            }
        }
    }
}
