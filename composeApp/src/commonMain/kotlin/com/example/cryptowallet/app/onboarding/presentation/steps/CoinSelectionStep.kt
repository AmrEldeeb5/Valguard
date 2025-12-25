package com.example.cryptowallet.app.onboarding.presentation.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptowallet.app.onboarding.domain.popularCoins
import com.example.cryptowallet.app.onboarding.presentation.components.CoinSelectionCard
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography

@Composable
fun CoinSelectionStep(
    selectedCoins: Set<String>,
    onToggleCoin: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // BarChart icon in gradient rounded square (pink to rose gradient)
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(colors.accentPink500, colors.loss)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "📊",
                fontSize = 40.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Title - "Choose Your Favorites"
        Text(
            text = "Choose Your Favorites",
            style = typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Subtitle
        Text(
            text = "Select coins to add to your watchlist",
            style = typography.bodyMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Hint text
        Text(
            text = "You can always change this later",
            style = typography.bodySmall,
            color = colors.textTertiary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 2x3 grid of coin cards
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            for (rowIndex in 0 until 3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (colIndex in 0 until 2) {
                        val coinIndex = rowIndex * 2 + colIndex
                        if (coinIndex < popularCoins.size) {
                            val coin = popularCoins[coinIndex]
                            CoinSelectionCard(
                                coin = coin,
                                isSelected = selectedCoins.contains(coin.symbol),
                                onToggle = { onToggleCoin(coin.symbol) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Selection badge (emerald green when coins selected)
        if (selectedCoins.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(colors.profit.copy(alpha = 0.1f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "✓ ${selectedCoins.size} ${if (selectedCoins.size == 1) "coin" else "coins"} selected",
                    style = typography.bodyMedium,
                    color = colors.profit,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun getSelectionCountText(count: Int): String {
    return when {
        count == 0 -> "Select at least one coin to continue"
        count == 1 -> "1 coin selected"
        else -> "$count coins selected"
    }
}
