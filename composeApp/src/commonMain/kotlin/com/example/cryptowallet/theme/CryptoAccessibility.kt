package com.example.cryptowallet.theme

import androidx.compose.runtime.compositionLocalOf

data class CryptoAccessibility(
    val reduceMotion: Boolean = false
)

val LocalCryptoAccessibility = compositionLocalOf { CryptoAccessibility() }
