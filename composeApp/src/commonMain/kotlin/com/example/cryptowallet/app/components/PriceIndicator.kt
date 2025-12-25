package com.example.cryptowallet.app.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.cryptowallet.app.realtime.domain.PriceDirection
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography

@Composable
fun PriceIndicator(
    direction: PriceDirection,
    showText: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    val (symbol, targetColor, description) = when (direction) {
        PriceDirection.UP -> Triple("▲", colors.profit, "Price increasing")
        PriceDirection.DOWN -> Triple("▼", colors.loss, "Price decreasing")
        PriceDirection.UNCHANGED -> Triple("—", colors.neutral, "Price unchanged")
    }
    
    // Animate color changes for smooth transitions
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 300),
        label = "priceIndicatorColor"
    )
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.semantics {
            contentDescription = description
        }
    ) {
        Text(
            text = symbol,
            color = animatedColor,
            style = typography.bodySmall
        )
        
        if (showText) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = when (direction) {
                    PriceDirection.UP -> "Up"
                    PriceDirection.DOWN -> "Down"
                    PriceDirection.UNCHANGED -> "Unchanged"
                },
                color = animatedColor,
                style = typography.labelSmall
            )
        }
    }
}
