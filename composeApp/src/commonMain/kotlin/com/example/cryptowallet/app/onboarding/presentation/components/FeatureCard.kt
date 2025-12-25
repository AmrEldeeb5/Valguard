package com.example.cryptowallet.app.onboarding.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cryptowallet.app.onboarding.domain.OnboardingFeature
import com.example.cryptowallet.theme.LocalCryptoAccessibility
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography
import kotlinx.coroutines.delay

/**
 * Feature highlight card for welcome and features steps.
 * Displays icon, title, and description with staggered animation.
 * Uses glassmorphism effect with transparent background.
 * Respects reduce motion accessibility setting.
 * 
 * Requirements: 2.8, 3.5, 3.6, 13.6
 */
@Composable
fun FeatureCard(
    feature: OnboardingFeature,
    index: Int = 0,
    animateIn: Boolean = true,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val accessibility = LocalCryptoAccessibility.current
    val reduceMotion = accessibility.reduceMotion
    
    // Skip animation if reduce motion is enabled
    val shouldAnimate = animateIn && !reduceMotion
    var isVisible by remember { mutableStateOf(!shouldAnimate) }
    
    LaunchedEffect(shouldAnimate) {
        if (shouldAnimate) {
            delay(index * 100L) // Staggered delay
            isVisible = true
        }
    }
    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = if (reduceMotion) 0 else 300)
    )
    
    val cardShape = RoundedCornerShape(16.dp)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha)
            .clip(cardShape)
            // Glassmorphism: nearly invisible background - lets background show through
            .background(Color.White.copy(alpha = 0.03f))
            // Visible border so you can see the card outline
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.12f),
                shape = cardShape
            )
            .padding(16.dp)
    ) {
        Column {
            // Icon with rounded square background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(feature.gradientColors)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = feature.iconType.emoji,
                    style = typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Title
            Text(
                text = feature.title,
                style = typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Description
            Text(
                text = feature.description,
                style = typography.bodySmall,
                color = colors.textSecondary
            )
        }
    }
}

/**
 * Compact feature card for welcome step highlights.
 * Uses glassmorphism effect with transparent background.
 * Respects reduce motion accessibility setting.
 */
@Composable
fun CompactFeatureCard(
    feature: OnboardingFeature,
    index: Int = 0,
    animateIn: Boolean = true,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val accessibility = LocalCryptoAccessibility.current
    val reduceMotion = accessibility.reduceMotion
    
    // Skip animation if reduce motion is enabled
    val shouldAnimate = animateIn && !reduceMotion
    var isVisible by remember { mutableStateOf(!shouldAnimate) }
    
    LaunchedEffect(shouldAnimate) {
        if (shouldAnimate) {
            delay(index * 100L)
            isVisible = true
        }
    }
    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = if (reduceMotion) 0 else 300)
    )
    
    val cardShape = RoundedCornerShape(16.dp)
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha)
            .clip(cardShape)
            // Glassmorphism: nearly invisible background - lets background show through
            .background(Color.White.copy(alpha = 0.03f))
            // Visible border so you can see the card outline
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.12f),
                shape = cardShape
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with rounded square background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(feature.gradientColors)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = feature.iconType.emoji,
                    style = typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.size(16.dp))
            
            Column {
                Text(
                    text = feature.title,
                    style = typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = feature.description,
                    style = typography.bodySmall,
                    color = colors.textSecondary
                )
            }
        }
    }
}
