package com.example.cryptowallet.app.onboarding.domain

import androidx.compose.ui.graphics.Color

sealed class OnboardingStep(
    val index: Int,
    val gradientColors: List<Color>
) {
    data object Welcome : OnboardingStep(
        index = 0,
        gradientColors = listOf(
            Color(0xFF3B82F6), // blue-500
            Color(0xFFA855F7)  // purple-500
        )
    )
    
    data object Features : OnboardingStep(
        index = 1,
        gradientColors = listOf(
            Color(0xFFA855F7), // purple-500
            Color(0xFFEC4899)  // pink-500
        )
    )
    
    data object CoinSelection : OnboardingStep(
        index = 2,
        gradientColors = listOf(
            Color(0xFFEC4899), // pink-500
            Color(0xFFFB7185)  // rose-400
        )
    )
    
    data object Notifications : OnboardingStep(
        index = 3,
        gradientColors = listOf(
            Color(0xFF34D399), // emerald-400
            Color(0xFF14B8A6)  // teal-500
        )
    )
    
    companion object {
        const val TOTAL_STEPS = 4
        
        fun fromIndex(index: Int): OnboardingStep = when (index) {
            0 -> Welcome
            1 -> Features
            2 -> CoinSelection
            3 -> Notifications
            else -> Welcome
        }
        
        fun all(): List<OnboardingStep> = listOf(Welcome, Features, CoinSelection, Notifications)
    }
}
