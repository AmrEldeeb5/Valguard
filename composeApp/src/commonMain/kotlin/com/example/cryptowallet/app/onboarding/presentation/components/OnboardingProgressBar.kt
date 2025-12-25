package com.example.cryptowallet.app.onboarding.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography

@Composable
fun OnboardingProgressBar(
    currentStep: Int,
    totalSteps: Int = 4,
    stepGradient: Brush,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val progress = ((currentStep + 1).toFloat() / totalSteps * 100).toInt()
    
    val accessibilityDescription = "Step ${currentStep + 1} of $totalSteps, $progress% complete"
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .semantics { contentDescription = accessibilityDescription },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Step indicator text
        Text(
            text = "Step ${currentStep + 1} of $totalSteps",
            style = typography.bodySmall,
            color = colors.textSecondary
        )
        
        // Progress segments
        Row(
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(totalSteps) { index ->
                val isActive = index <= currentStep
                val animatedWidth by animateFloatAsState(
                    targetValue = if (isActive) 1f else 0f,
                    animationSpec = tween(durationMillis = 300)
                )
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(colors.cardBackground)
                ) {
                    if (isActive) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(animatedWidth)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(stepGradient)
                        )
                    }
                }
            }
        }
        
        // Percentage text
        Text(
            text = "$progress%",
            style = typography.bodySmall,
            color = colors.textSecondary
        )
    }
}

fun calculateProgressPercentage(currentStep: Int, totalSteps: Int = 4): Int {
    return ((currentStep + 1).toFloat() / totalSteps * 100).toInt()
}
