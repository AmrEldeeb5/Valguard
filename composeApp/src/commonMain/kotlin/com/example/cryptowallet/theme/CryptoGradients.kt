package com.example.cryptowallet.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object CryptoGradients {
    
    @Composable
    fun blueToPerple(): Brush {
        val colors = LocalCryptoColors.current
        return Brush.horizontalGradient(
            colors = listOf(
                colors.accentBlue500,
                colors.accentPurple500
            )
        )
    }
    
    @Composable
    fun brandGradient(): Brush {
        val colors = LocalCryptoColors.current
        return Brush.horizontalGradient(
            colors = listOf(
                colors.accentBlue400,
                colors.accentPurple400,
                colors.accentPink400
            )
        )
    }
    
    @Composable
    fun brandGradientVertical(): Brush {
        val colors = LocalCryptoColors.current
        return Brush.verticalGradient(
            colors = listOf(
                colors.accentBlue400,
                colors.accentPurple400,
                colors.accentPink400
            )
        )
    }
    
    @Composable
    fun subtleCardGradient(alpha: Float = 0.2f): Brush {
        val colors = LocalCryptoColors.current
        return Brush.horizontalGradient(
            colors = listOf(
                colors.accentBlue500.copy(alpha = alpha),
                colors.accentPurple500.copy(alpha = alpha),
                colors.accentPink500.copy(alpha = alpha)
            )
        )
    }
    
    @Composable
    fun profitGradient(): Brush {
        val colors = LocalCryptoColors.current
        return Brush.verticalGradient(
            colors = listOf(
                colors.profit,
                colors.profit.copy(alpha = 0.3f)
            )
        )
    }
    
    @Composable
    fun lossGradient(): Brush {
        val colors = LocalCryptoColors.current
        return Brush.verticalGradient(
            colors = listOf(
                colors.loss,
                colors.loss.copy(alpha = 0.3f)
            )
        )
    }
    
    fun coinIconGradient(symbol: String): Brush {
        val hash = symbol.hashCode()
        val hue1 = (hash and 0xFF) / 255f * 360f
        val hue2 = ((hash shr 8) and 0xFF) / 255f * 360f
        
        return Brush.linearGradient(
            colors = listOf(
                Color.hsl(hue1, 0.7f, 0.5f),
                Color.hsl(hue2, 0.7f, 0.4f)
            )
        )
    }
}

@Composable
fun CryptoColors.activeTabGradient(): Brush = Brush.horizontalGradient(
    colors = listOf(accentBlue500, accentPurple500)
)

@Composable
fun CryptoColors.titleGradient(): Brush = Brush.horizontalGradient(
    colors = listOf(accentBlue400, accentPurple400, accentPink400)
)
