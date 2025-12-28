/**
 * SplashScreen.kt
 *
 * Main splash screen for the CryptoVault application.
 * Displays animated branding, loading progress, and feature highlights
 * during the 3-second app initialization sequence.
 *
 * The splash screen features:
 * - Animated background with pulsing orbs
 * - Animated logo with rotating rings
 * - Gradient text branding
 * - Feature pills highlighting key app capabilities
 * - Progress indicator with percentage
 * - Staggered entrance animations
 * - Smooth fade-out transition
 * - Adaptive layout for different screen sizes (phone, tablet, desktop)
 *
 * Total duration: ~4.5 seconds (3s loading + 0.5s hold + 1s fade-out)
 */
package com.example.cryptowallet.app.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.cryptowallet.app.splash.components.*
import com.example.cryptowallet.theme.*
import kotlinx.coroutines.delay

/**
 * Splash screen composable with animated loading sequence.
 *
 * Orchestrates the entire splash screen experience including:
 * - Progress simulation (0% → 100% over 3 seconds)
 * - Hold at 100% for 500ms
 * - Fade-out animation over 1 second
 * - Navigation callback after completion
 * - Adaptive layout based on screen size
 *
 * All UI elements use staggered entrance animations for a progressive
 * reveal effect. The entire screen fades out smoothly before navigating
 * to the main app.
 *
 * Layout adapts to screen size:
 * - Compact (phone): Vertical layout with smaller elements
 * - Medium (tablet): Larger elements with more spacing
 * - Expanded (desktop/foldable): Maximum size with generous spacing
 *
 * @param onSplashComplete Callback invoked when splash sequence completes
 */
@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var progress by remember { mutableStateOf(0f) }
    var alpha by remember { mutableStateOf(1f) }
    
    // Get window size class for adaptive layout
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isCompact = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
    val isExpanded = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
    
    // Adaptive sizing based on screen size
    val logoScale = when {
        isExpanded -> 1.3f
        isCompact -> 0.9f
        else -> 1.0f
    }
    
    val titleSize = when {
        isExpanded -> 64.sp
        isCompact -> 40.sp
        else -> 48.sp
    }
    
    val contentPadding = when {
        isExpanded -> 64.dp
        isCompact -> 24.dp
        else -> 32.dp
    }
    
    val verticalSpacing = when {
        isExpanded -> 56.dp
        isCompact -> 32.dp
        else -> 48.dp
    }
    
    // Simulate loading progress
    LaunchedEffect(Unit) {
        while (progress < 1f) {
            delay(30) // Update every 30ms
            progress = (progress + 0.02f).coerceAtMost(1f) // Increment by 2%, cap at 100%
        }
        // Hold at 100% for 500ms
        delay(500)
        // Fade out
        alpha = 0f
        delay(1000) // Wait for fade animation
        onSplashComplete()
    }
    
    // Fade out animation
    val animatedAlpha by animateFloatAsState(
        targetValue = alpha,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { this.alpha = animatedAlpha },
        contentAlignment = Alignment.Center
    ) {
        // Animated background
        AnimatedCryptoBackground()
        
        // Floating particles
        FloatingParticles(
            modifier = Modifier.fillMaxSize(),
            particleCount = 50 // Ultra fast streaming effect
        )
        
        // Main content with adaptive layout
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(contentPadding)
                .then(
                    if (isExpanded) {
                        Modifier.widthIn(max = 800.dp) // Constrain width on large screens
                    } else {
                        Modifier
                    }
                )
        ) {
            // Animated logo with adaptive scale
            Box(
                modifier = Modifier.graphicsLayer {
                    scaleX = logoScale
                    scaleY = logoScale
                }
            ) {
                AnimatedLogo()
            }
            
            Spacer(modifier = Modifier.height(verticalSpacing * 0.67f))
            
            // App name with gradient (adaptive size)
            GradientText(
                text = "CryptoVault",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = titleSize,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Tagline with gradient (adaptive size)
            GradientText(
                text = "Your Premium Crypto Companion",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = when {
                        isExpanded -> 24.sp
                        isCompact -> 16.sp
                        else -> 20.sp
                    }
                )
            )
            
            Spacer(modifier = Modifier.height(verticalSpacing))
            
            // Feature pills (adaptive layout)
            if (isCompact) {
                // Vertical layout for compact screens
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeaturePill(
                        icon = Icons.Default.Refresh,
                        text = "Real-time",
                        color = Yellow400,
                        isCompact = true
                    )
                    FeaturePill(
                        icon = Icons.Default.Lock,
                        text = "Secure",
                        color = Emerald400,
                        isCompact = true
                    )
                    FeaturePill(
                        icon = Icons.Default.Star,
                        text = "Analytics",
                        color = Blue400,
                        isCompact = true
                    )
                }
            } else {
                // Horizontal layout for medium/expanded screens
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FeaturePill(
                        icon = Icons.Default.Refresh,
                        text = "Real-time",
                        color = Yellow400,
                        isCompact = false
                    )
                    
                    Spacer(modifier = Modifier.width(if (isExpanded) 16.dp else 8.dp))
                    
                    FeaturePill(
                        icon = Icons.Default.Lock,
                        text = "Secure",
                        color = Emerald400,
                        isCompact = false
                    )
                    
                    Spacer(modifier = Modifier.width(if (isExpanded) 16.dp else 8.dp))
                    
                    FeaturePill(
                        icon = Icons.Default.Star,
                        text = "Analytics",
                        color = Blue400,
                        isCompact = false
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(verticalSpacing))
            
            // Progress bar
            SplashProgressBar(progress = progress)
            
            Spacer(modifier = Modifier.height(verticalSpacing * 1.33f))
            
            // Version info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Version 1.0.0",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = if (isExpanded) 13.sp else 11.sp
                    ),
                    color = Slate600
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "© 2024 CryptoVault. All rights reserved.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = if (isExpanded) 13.sp else 11.sp
                    ),
                    color = Slate600
                )
            }
        }
    }
}

/**
 * Feature pill component displaying an icon and text label.
 *
 * Small UI element that highlights a key app feature with:
 * - Icon in specified accent color
 * - Text label
 * - Semi-transparent background
 * - Subtle border
 * - Adaptive sizing based on screen size
 *
 * Used to showcase "Real-time", "Secure", and "Analytics" features
 * on the splash screen.
 *
 * @param icon Material icon to display
 * @param text Feature label text
 * @param color Accent color for the icon
 * @param isCompact Whether the screen is in compact mode (phone)
 */
@Composable
private fun FeaturePill(
    icon: ImageVector,
    text: String,
    color: Color,
    isCompact: Boolean
) {
    val iconSize = if (isCompact) 16.dp else 14.dp
    val fontSize = if (isCompact) 12.sp else 11.sp
    val horizontalPadding = if (isCompact) 16.dp else 12.dp
    val verticalPadding = if (isCompact) 8.dp else 6.dp
    
    Row(
        modifier = Modifier
            .background(
                color = Slate800.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = Slate700.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = fontSize
            ),
            color = Slate300,
            maxLines = 1,
            softWrap = false
        )
    }
}
