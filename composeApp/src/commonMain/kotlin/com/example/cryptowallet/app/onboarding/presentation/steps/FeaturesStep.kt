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
import androidx.compose.foundation.layout.width
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
import com.example.cryptowallet.app.onboarding.domain.gridFeatures
import com.example.cryptowallet.app.onboarding.presentation.components.FeatureCard
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography

@Composable
fun FeaturesStep(
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
        // Zap icon in gradient rounded square (rounded-2xl)
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(colors.accentPurple500, colors.accentPink500)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "⚡",
                fontSize = 40.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Title - "Everything You Need"
        Text(
            text = "Everything You Need",
            style = typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Subtitle
        Text(
            text = "Track, analyze, and stay ahead of the market",
            style = typography.bodyMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 2x2 grid of feature cards
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FeatureCard(
                    feature = gridFeatures[0],
                    index = 0,
                    modifier = Modifier.weight(1f)
                )
                FeatureCard(
                    feature = gridFeatures[1],
                    index = 1,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FeatureCard(
                    feature = gridFeatures[2],
                    index = 2,
                    modifier = Modifier.weight(1f)
                )
                FeatureCard(
                    feature = gridFeatures[3],
                    index = 3,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // "100% Free Forever" info banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            colors.accentBlue600.copy(alpha = 0.2f),
                            colors.accentPurple600.copy(alpha = 0.2f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                // Shield icon
                Text(
                    text = "🛡️",
                    fontSize = 20.sp
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "100% Free Forever",
                        style = typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.accentBlue400
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No credit card required. All premium features included.",
                        style = typography.bodySmall,
                        color = colors.textSecondary
                    )
                }
            }
        }
    }
}
