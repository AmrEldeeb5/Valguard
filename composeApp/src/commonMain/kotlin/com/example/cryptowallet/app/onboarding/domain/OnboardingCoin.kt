package com.example.cryptowallet.app.onboarding.domain

import androidx.compose.ui.graphics.Color

data class OnboardingCoin(
    val symbol: String,
    val name: String,
    val icon: String,
    val gradientColors: List<Color>
)

val popularCoins = listOf(
    OnboardingCoin(
        symbol = "BTC",
        name = "Bitcoin",
        icon = "₿",
        gradientColors = listOf(
            Color(0xFFF97316), // orange-500
            Color(0xFFEAB308)  // yellow-500
        )
    ),
    OnboardingCoin(
        symbol = "ETH",
        name = "Ethereum",
        icon = "Ξ",
        gradientColors = listOf(
            Color(0xFF3B82F6), // blue-500
            Color(0xFFA855F7)  // purple-500
        )
    ),
    OnboardingCoin(
        symbol = "BNB",
        name = "Binance",
        icon = "B",
        gradientColors = listOf(
            Color(0xFFFACC15), // yellow-400
            Color(0xFFF97316)  // orange-500
        )
    ),
    OnboardingCoin(
        symbol = "SOL",
        name = "Solana",
        icon = "S",
        gradientColors = listOf(
            Color(0xFFA855F7), // purple-500
            Color(0xFFEC4899)  // pink-500
        )
    ),
    OnboardingCoin(
        symbol = "ADA",
        name = "Cardano",
        icon = "A",
        gradientColors = listOf(
            Color(0xFF60A5FA), // blue-400
            Color(0xFF22D3EE)  // cyan-500
        )
    ),
    OnboardingCoin(
        symbol = "XRP",
        name = "Ripple",
        icon = "X",
        gradientColors = listOf(
            Color(0xFF9CA3AF), // gray-400
            Color(0xFF64748B)  // slate-500
        )
    )
)
