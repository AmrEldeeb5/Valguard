package com.example.cryptowallet.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class CryptoShapes(
    val card: Shape = RoundedCornerShape(12.dp),
    val button: Shape = RoundedCornerShape(8.dp),
    val chip: Shape = RoundedCornerShape(16.dp),
    val bottomSheet: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    val dialog: Shape = RoundedCornerShape(16.dp),
    val inputField: Shape = RoundedCornerShape(8.dp)
)

val DefaultCryptoShapes = CryptoShapes()

val LocalCryptoShapes = compositionLocalOf { DefaultCryptoShapes }
