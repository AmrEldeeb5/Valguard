package com.example.cryptowallet.app.onboarding.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptowallet.app.onboarding.presentation.components.OnboardingBackground
import com.example.cryptowallet.app.onboarding.presentation.components.OnboardingButton
import com.example.cryptowallet.app.onboarding.presentation.components.OnboardingProgressBar
import com.example.cryptowallet.app.onboarding.presentation.steps.CoinSelectionStep
import com.example.cryptowallet.app.onboarding.presentation.steps.FeaturesStep
import com.example.cryptowallet.app.onboarding.presentation.steps.NotificationsStep
import com.example.cryptowallet.app.onboarding.presentation.steps.WelcomeStep
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoTypography
import org.koin.compose.viewmodel.koinViewModel

/**
 * Main onboarding screen composable.
 * Manages the 4-step onboarding flow with animations and navigation.
 * 
 * Requirements: 1.1, 1.6, 1.7, 6.1, 6.4, 6.5, 11.1, 11.2, 11.3, 11.5
 */
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val canProceed by viewModel.canProceed.collectAsState()
    val stepColors by viewModel.currentStepColors.collectAsState()
    
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    // Set navigation callback
    LaunchedEffect(Unit) {
        viewModel.setNavigationCallback(onComplete)
    }
    
    val stepGradient = Brush.horizontalGradient(stepColors)
    
    // Handle Android back button
    BackHandler(enabled = state.currentStep > 0) {
        viewModel.onEvent(OnboardingEvent.PreviousStep)
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Animated background
        OnboardingBackground()
        
        // Main content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            // Top bar with back, progress, and skip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back button
                AnimatedVisibility(
                    visible = state.currentStep > 0,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(colors.cardBackground.copy(alpha = 0.5f))
                            .clickable { viewModel.onEvent(OnboardingEvent.PreviousStep) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Go back",
                            tint = colors.textPrimary
                        )
                    }
                }
                
                if (state.currentStep == 0) {
                    Spacer(modifier = Modifier.size(40.dp))
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Skip button
                AnimatedVisibility(
                    visible = state.currentStep < 3,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "Skip",
                        style = typography.labelLarge,
                        color = colors.textSecondary,
                        modifier = Modifier
                            .clickable { viewModel.onEvent(OnboardingEvent.SkipToEnd) }
                            .padding(8.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress bar
            OnboardingProgressBar(
                currentStep = state.currentStep,
                stepGradient = stepGradient
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Main content card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(colors.cardBackground.copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Step content with animated transitions
                    AnimatedContent(
                        targetState = state.currentStep,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInHorizontally { it } + fadeIn() togetherWith
                                    slideOutHorizontally { -it } + fadeOut()
                            } else {
                                slideInHorizontally { -it } + fadeIn() togetherWith
                                    slideOutHorizontally { it } + fadeOut()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) { step ->
                        when (step) {
                            0 -> WelcomeStep()
                            1 -> FeaturesStep()
                            2 -> CoinSelectionStep(
                                selectedCoins = state.selectedCoins,
                                onToggleCoin = { viewModel.onEvent(OnboardingEvent.ToggleCoin(it)) }
                            )
                            3 -> NotificationsStep(
                                notificationsEnabled = state.notificationsEnabled,
                                onToggleNotifications = { viewModel.onEvent(OnboardingEvent.ToggleNotifications) }
                            )
                        }
                    }
                    
                    // Continue button
                    OnboardingButton(
                        currentStep = state.currentStep,
                        enabled = canProceed && !state.isTransitioning,
                        gradient = stepGradient,
                        onClick = { viewModel.onEvent(OnboardingEvent.NextStep) },
                        modifier = Modifier.padding(24.dp)
                    )
                    
                    // Skip for now text (only on steps 0-2)
                    if (state.currentStep < 3) {
                        Text(
                            text = "Skip for now",
                            style = typography.bodyMedium,
                            color = colors.textSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.onEvent(OnboardingEvent.SkipToEnd) }
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
        
        // Skip confirmation dialog
        if (state.showSkipDialog) {
            SkipConfirmationDialog(
                onConfirm = { viewModel.onEvent(OnboardingEvent.ConfirmSkip) },
                onDismiss = { viewModel.onEvent(OnboardingEvent.DismissSkipDialog) }
            )
        }
        
        // Success animation overlay
        if (state.showSuccessAnimation) {
            SuccessAnimationOverlay()
        }
    }
}

/**
 * Skip confirmation dialog.
 * Requirements: 6.7, 6.8
 */
@Composable
private fun SkipConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Skip onboarding?",
                style = typography.titleMedium,
                color = colors.textPrimary
            )
        },
        text = {
            Text(
                text = "You can always customize your settings later in the app.",
                style = typography.bodyMedium,
                color = colors.textSecondary
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = "Skip",
                    color = colors.accentBlue500
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Continue setup",
                    color = colors.textSecondary
                )
            }
        },
        containerColor = colors.cardBackground
    )
}

/**
 * Success animation overlay shown after completing onboarding.
 * Requirements: 9.1, 9.2, 9.3, 9.4, 9.5, 9.6
 */
@Composable
private fun SuccessAnimationOverlay() {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(500)
    )
    
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(300)
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        colors.profit.copy(alpha = 0.9f),
                        colors.profit.copy(alpha = 0.7f)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Animated checkmark
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = colors.profit,
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Welcome text
            Text(
                text = "Welcome aboard!",
                style = typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Your crypto journey begins now",
                style = typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}
