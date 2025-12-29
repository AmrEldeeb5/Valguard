/**
 * FeaturesStep.kt
 *
 * Second step of the onboarding flow showcasing app capabilities.
 * Displays a 2x2 grid of feature cards and a "free forever" banner.
 *
 * Features:
 * - Animated zap icon header
 * - 2x2 feature grid with staggered animations
 * - "100% Free Forever" promotional banner
 *
 * @see OnboardingScreen for the parent container
 * @see gridFeatures for the displayed features
 */
package com.example.cryptovault.app.onboarding.presentation.steps

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptovault.app.onboarding.domain.gridFeatures
import com.example.cryptovault.app.onboarding.presentation.components.FeatureCard
import com.example.cryptovault.theme.AppTheme
import com.example.cryptovault.theme.LocalCryptoColors
import com.example.cryptovault.theme.LocalCryptoTypography

/**
 * Features step content for onboarding.
 *
 * Displays the feature showcase with header, 2x2 feature grid,
 * and promotional banner.
 *
 * @param modifier Optional modifier for the component
 */
@Composable
fun FeaturesStep(
    modifier: Modifier = Modifier
) {
    val dimensions = AppTheme.dimensions
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensions.cardPadding * 2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FeaturesHeader()
        
        Spacer(modifier = Modifier.height(dimensions.verticalSpacing))
        
        // 2x2 grid of feature cards
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensions.itemSpacing)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensions.itemSpacing)
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
                horizontalArrangement = Arrangement.spacedBy(dimensions.itemSpacing)
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
        
        Spacer(modifier = Modifier.height(dimensions.verticalSpacing))
        
        FreeForeverBanner()
    }
}

/**
 * Header section with zap icon and title text.
 *
 * @param modifier Optional modifier for the component
 */
@Composable
fun FeaturesHeader(
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val dimensions = AppTheme.dimensions
    
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Zap icon in gradient rounded square
        Box(
            modifier = Modifier
                .size(dimensions.appIconSize + 16.dp)
                .clip(RoundedCornerShape(dimensions.cardCornerRadius))
                .background(
                    Brush.linearGradient(
                        colors = listOf(colors.accentPurple500, colors.accentPink500)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "⚡",
                fontSize = (dimensions.appIconSize.value * 0.5f).sp
            )
        }
        
        Spacer(modifier = Modifier.height(dimensions.verticalSpacing))
        
        Text(
            text = "Everything You Need",
            style = typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(dimensions.smallSpacing))
        
        Text(
            text = "Track, analyze, and stay ahead of the market",
            style = typography.bodyMedium,
            color = colors.textSecondary,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Promotional banner highlighting free features.
 *
 * @param modifier Optional modifier for the component
 */
@Composable
fun FreeForeverBanner(
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val dimensions = AppTheme.dimensions
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensions.cardCornerRadius))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        colors.accentBlue600.copy(alpha = 0.2f),
                        colors.accentPurple600.copy(alpha = 0.2f)
                    )
                )
            )
            .padding(dimensions.cardPadding)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "🛡️",
                fontSize = (dimensions.coinIconSize.value * 0.42f).sp
            )
            Spacer(modifier = Modifier.width(dimensions.itemSpacing))
            Column {
                Text(
                    text = "100% Free Forever",
                    style = typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.accentBlue400
                )
                Spacer(modifier = Modifier.height(dimensions.smallSpacing / 2))
                Text(
                    text = "No credit card required. All premium features included.",
                    style = typography.bodySmall,
                    color = colors.textSecondary
                )
            }
        }
    }
}

