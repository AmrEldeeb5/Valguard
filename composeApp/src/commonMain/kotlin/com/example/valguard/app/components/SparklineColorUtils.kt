/**
 * SparklineColorUtils.kt
 *
 * Utility functions for determining sparkline colors based on coin type and price movement.
 * Provides semantic color selection that reduces false emotional signals for stablecoins
 * and minimal price movements.
 *
 * Features:
 * - Neutral colors for stablecoins (USDT, USDC, etc.)
 * - Neutral colors for minimal price movements (< 0.5%)
 * - Standard profit/loss colors for significant movements
 *
 * @see CoinClassification for stablecoin detection logic
 */
package com.example.valguard.app.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.valguard.app.coins.domain.CoinClassification
import com.example.valguard.theme.LocalCryptoColors

/**
 * Determines the appropriate sparkline color based on coin type and price movement.
 *
 * This function applies semantic color logic to avoid false emotional signals:
 * - Stablecoins (USDT, USDC, etc.) use neutral colors regardless of movement
 * - Minimal movements (< 0.5%) use neutral colors to avoid alarm
 * - Significant movements use standard profit (green) or loss (red) colors
 *
 * @param symbol The cryptocurrency symbol (e.g., "BTC", "USDT")
 * @param changePercent The price change percentage (e.g., 0.3, -2.5, 5.0)
 * @param isPositive Whether the price change is positive
 * @return The appropriate color for the sparkline
 *
 * @example
 * ```kotlin
 * // Stablecoin - returns neutral color
 * val usdtColor = getSparklineColor("USDT", 0.1, true)
 *
 * // Minimal movement - returns neutral color
 * val btcMinimalColor = getSparklineColor("BTC", 0.3, true)
 *
 * // Significant positive movement - returns profit color
 * val ethColor = getSparklineColor("ETH", 5.0, true)
 *
 * // Significant negative movement - returns loss color
 * val bnbColor = getSparklineColor("BNB", -3.0, false)
 * ```
 */
@Composable
fun getSparklineColor(
    symbol: String,
    changePercent: Double,
    isPositive: Boolean
): Color {
    val colors = LocalCryptoColors.current
    
    return when {
        CoinClassification.isStablecoin(symbol) || CoinClassification.isMinimalMovement(changePercent) -> {
            // Use neutral movement color for stablecoins or minimal movement
            colors.neutralMovement
        }
        isPositive -> colors.profit
        else -> colors.loss
    }
}
