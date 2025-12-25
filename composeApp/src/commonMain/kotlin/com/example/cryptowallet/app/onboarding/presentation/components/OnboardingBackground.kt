package com.example.cryptowallet.app.onboarding.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptowallet.theme.LocalCryptoAccessibility
import com.example.cryptowallet.theme.LocalCryptoColors
import kotlin.math.sin
import kotlin.random.Random

/**
 * Animated background for the onboarding flow.
 * Features pulsing gradient orbs and floating crypto symbols.
 * Matches React design with blue, purple, and pink animated orbs.
 * 
 * Requirements: 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7
 */
@Composable
fun OnboardingBackground(
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val accessibility = LocalCryptoAccessibility.current
    val reduceMotion = accessibility.reduceMotion
    
    // Static alpha values when reduce motion is enabled
    val staticOrb1Alpha = 0.1f
    val staticOrb2Alpha = 0.15f
    val staticOrb3Alpha = 0.05f
    
    // Animated alpha values for orbs
    val orb1Alpha: Float
    val orb2Alpha: Float
    val orb3Alpha: Float
    
    if (reduceMotion) {
        orb1Alpha = staticOrb1Alpha
        orb2Alpha = staticOrb2Alpha
        orb3Alpha = staticOrb3Alpha
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "background")
        
        val animatedOrb1Alpha by infiniteTransition.animateFloat(
            initialValue = 0.05f,
            targetValue = 0.15f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "orb1"
        )
        
        val animatedOrb2Alpha by infiniteTransition.animateFloat(
            initialValue = 0.1f,
            targetValue = 0.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(4000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "orb2"
        )
        
        val animatedOrb3Alpha by infiniteTransition.animateFloat(
            initialValue = 0.03f,
            targetValue = 0.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "orb3"
        )
        
        orb1Alpha = animatedOrb1Alpha
        orb2Alpha = animatedOrb2Alpha
        orb3Alpha = animatedOrb3Alpha
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        // Canvas for orbs
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background gradient (slate-950 -> slate-900 -> slate-950)
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF020617), // slate-950
                        Color(0xFF0F172A), // slate-900
                        Color(0xFF020617)  // slate-950
                    )
                )
            )
            
            // Blue orb - top left
            drawAnimatedOrb(
                center = Offset(size.width * 0.25f, size.height * 0.25f),
                radius = size.width * 0.4f,
                color = colors.accentBlue500,
                alpha = orb1Alpha
            )
            
            // Purple orb - bottom right
            drawAnimatedOrb(
                center = Offset(size.width * 0.75f, size.height * 0.75f),
                radius = size.width * 0.4f,
                color = colors.accentPurple500,
                alpha = orb2Alpha
            )
            
            // Pink orb - center
            drawAnimatedOrb(
                center = Offset(size.width * 0.5f, size.height * 0.5f),
                radius = size.width * 0.35f,
                color = colors.accentPink500,
                alpha = orb3Alpha
            )
        }
        
        // Floating crypto symbols (skip if reduce motion enabled)
        if (!reduceMotion) {
            FloatingCryptoSymbols()
        }
    }
}

/**
 * Draws an animated orb with radial gradient.
 */
private fun DrawScope.drawAnimatedOrb(
    center: Offset,
    radius: Float,
    color: Color,
    alpha: Float
) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                color.copy(alpha = alpha),
                Color.Transparent
            ),
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center
    )
}

/**
 * Data class for floating symbol configuration.
 */
private data class SymbolData(
    val symbol: String,
    val x: Int,
    val y: Int,
    val size: Int,
    val alpha: Float,
    val durationY: Int,
    val durationX: Int,
    val amplitudeY: Float,
    val amplitudeX: Float,
    val delay: Int
)

/**
 * Floating crypto symbols scattered randomly across the screen.
 * Each symbol floats in a unique pattern.
 */
@Composable
private fun FloatingCryptoSymbols() {
    val symbols = listOf("₿", "Ξ", "◎", "₳", "Ł", "◈", "⟐", "✦", "⬡", "◇")
    
    // Generate random positions and properties for 20 symbols
    val symbolData = remember {
        (0 until 20).map { index ->
            SymbolData(
                symbol = symbols[index % symbols.size],
                x = Random.nextInt(10, 350),
                y = Random.nextInt(50, 750),
                size = Random.nextInt(12, 24),
                alpha = Random.nextFloat() * 0.06f + 0.02f, // 0.02 to 0.08
                durationY = Random.nextInt(4000, 8000),
                durationX = Random.nextInt(5000, 10000),
                amplitudeY = Random.nextFloat() * 30f + 15f, // 15 to 45
                amplitudeX = Random.nextFloat() * 20f + 10f, // 10 to 30
                delay = Random.nextInt(0, 2000)
            )
        }
    }
    
    symbolData.forEach { data ->
        FloatingSymbol(data)
    }
}

/**
 * Individual floating symbol with smooth X and Y movement.
 */
@Composable
private fun FloatingSymbol(data: SymbolData) {
    val infiniteTransition = rememberInfiniteTransition(label = "symbol_${data.x}_${data.y}")
    
    // Vertical floating animation
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = data.amplitudeY,
        animationSpec = infiniteRepeatable(
            animation = tween(data.durationY, delayMillis = data.delay, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "y_${data.x}_${data.y}"
    )
    
    // Horizontal drifting animation
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -data.amplitudeX / 2,
        targetValue = data.amplitudeX / 2,
        animationSpec = infiniteRepeatable(
            animation = tween(data.durationX, delayMillis = data.delay, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "x_${data.x}_${data.y}"
    )
    
    // Alpha pulsing animation
    val alphaMultiplier by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(data.durationY + 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha_${data.x}_${data.y}"
    )
    
    Text(
        text = data.symbol,
        fontSize = data.size.sp,
        color = Color.White,
        modifier = Modifier
            .offset(
                x = (data.x + offsetX).dp,
                y = (data.y + offsetY).dp
            )
            .alpha(data.alpha * alphaMultiplier)
    )
}
