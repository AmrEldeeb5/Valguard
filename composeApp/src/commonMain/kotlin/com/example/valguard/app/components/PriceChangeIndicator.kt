/**
 * PriceChangeIndicator.kt
 *
 * Reusable composable component that displays a price change percentage with
 * an optional directional icon (up/down arrow).
 *
 * Features:
 * - Directional icons for positive/negative changes
 * - No icon for zero change
 * - Color consistency between icon and text
 * - Respects neutral color logic for stablecoins and minimal movements
 * - Full accessibility support with content descriptions
 *
 * @see CoinCard for usage in coin list cards
 * @see ExpandableCoinCard for usage in expanded coin details
 * @see PortfolioValueCard for usage in portfolio summary
 */
package com.example.valguard.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.valguard.app.coins.domain.CoinClassification
import com.example.valguard.theme.LocalCryptoColors
import com.example.valguard.theme.LocalCryptoTypography
import org.jetbrains.compose.resources.painterResource
import valguard.composeapp.generated.resources.Res
import valguard.composeapp.generated.resources.material_symbols_light__line_end_arrow_notch_rounded
import valguard.composeapp.generated.resources.solar__course_down_outline
import valguard.composeapp.generated.resources.solar__course_up_outline

/**
 * Displays a price change percentage with a directional icon.
 *
 * The component automatically selects the appropriate icon based on the magnitude
 * and sign of the change:
 * - Significant positive change (> +0.2%): Course Up icon (upward arrow)
 * - Significant negative change (< -0.2%): Course Down icon (downward arrow)
 * - Minimal movement (-0.2% to +0.2%): Horizontal arrow icon (minimal change indicator)
 *
 * The icon and text colors are always synchronized, respecting the existing
 * neutral color logic for stablecoins and minimal movements.
 *
 * @param changePercent The raw percentage value (e.g., 2.40, -1.50, 0.08)
 * @param formattedChange The pre-formatted string to display (e.g., "+2.40%", "-1.50%", "+0.08%")
 * @param isPositive Whether the change is positive (used for color selection)
 * @param modifier Optional modifier for the component
 * @param textStyle Typography style for the percentage text (default: bodySmall)
 * @param iconSize Size of the directional icon (default: 16.dp)
 * @param spacing Horizontal spacing between icon and text (default: 4.dp)
 * @param color Optional color override (defaults to theme profit/loss colors)
 *
 * @example
 * ```kotlin
 * // Significant positive change with green upward arrow
 * PriceChangeIndicator(
 *     changePercent = 2.40,
 *     formattedChange = "+2.40%",
 *     isPositive = true
 * )
 *
 * // Significant negative change with red downward arrow
 * PriceChangeIndicator(
 *     changePercent = -1.50,
 *     formattedChange = "-1.50%",
 *     isPositive = false
 * )
 *
 * // Minimal movement with neutral horizontal arrow
 * PriceChangeIndicator(
 *     changePercent = 0.08,
 *     formattedChange = "+0.08%",
 *     isPositive = true
 * )
 *
 * // Larger icon for expanded views
 * PriceChangeIndicator(
 *     changePercent = 5.0,
 *     formattedChange = "+5.00%",
 *     isPositive = true,
 *     iconSize = 20.dp,
 *     textStyle = MaterialTheme.typography.titleMedium
 * )
 * ```
 */
@Composable
fun PriceChangeIndicator(
    changePercent: Double,
    formattedChange: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalCryptoTypography.current.bodySmall,
    iconSize: Dp = 16.dp,
    spacing: Dp = 4.dp,
    color: Color? = null
) {
    val colors = LocalCryptoColors.current
    
    // Determine the color to use (either provided or default profit/loss)
    val displayColor = color ?: if (isPositive) colors.profit else colors.loss
    
    // Build accessibility description
    val isMinimalMovement = CoinClassification.isMinimalMovement(changePercent)
    val accessibilityDescription = buildString {
        when {
            isMinimalMovement -> append("Minimal change")
            changePercent > 0 -> append("Price increased")
            else -> append("Price decreased")
        }
        append(" by ")
        append(formattedChange)
    }
    
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = accessibilityDescription
        }
    ) {
        // Show icon for all changes
        // Use horizontal arrow for minimal movements (-0.5% to +0.5%)
        val isMinimalMovement = CoinClassification.isMinimalMovement(changePercent)
        
        Icon(
            painter = painterResource(
                when {
                    isMinimalMovement -> Res.drawable.material_symbols_light__line_end_arrow_notch_rounded
                    changePercent > 0 -> Res.drawable.solar__course_up_outline
                    else -> Res.drawable.solar__course_down_outline
                }
            ),
            contentDescription = when {
                isMinimalMovement -> "Minimal change"
                changePercent > 0 -> "Price increased"
                else -> "Price decreased"
            },
            tint = displayColor,
            modifier = Modifier.size(iconSize)
        )
        
        Spacer(modifier = Modifier.width(spacing))
        
        Text(
            text = formattedChange,
            style = textStyle,
            color = displayColor
        )
    }
}
