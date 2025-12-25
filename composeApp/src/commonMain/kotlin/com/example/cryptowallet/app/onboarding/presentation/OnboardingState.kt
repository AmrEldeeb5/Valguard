package com.example.cryptowallet.app.onboarding.presentation

data class OnboardingState(
    val currentStep: Int = 0,
    val selectedCoins: Set<String> = emptySet(),
    val notificationsEnabled: Boolean = false,
    val isTransitioning: Boolean = false,
    val showSkipDialog: Boolean = false,
    val showSuccessAnimation: Boolean = false,
    val isLoading: Boolean = true
) {
    val progressPercentage: Int
        get() = ((currentStep + 1) * 25)
    
    val stepText: String
        get() = "Step ${currentStep + 1} of 4"
    
    val filledSegments: Int
        get() = currentStep + 1
    
    val isLastStep: Boolean
        get() = currentStep == 3
    
    val buttonText: String
        get() = if (isLastStep) "Get Started" else "Continue"
    
    val showBackButton: Boolean
        get() = currentStep > 0
    
    val showSkipButton: Boolean
        get() = currentStep < 3
    
    val selectedCoinsCount: Int
        get() = selectedCoins.size
    
    val selectionBadgeText: String
        get() = "✓ $selectedCoinsCount ${if (selectedCoinsCount == 1) "coin" else "coins"} selected"
}

sealed class OnboardingEvent {
    data object NextStep : OnboardingEvent()
    
    data object PreviousStep : OnboardingEvent()
    
    data object SkipToEnd : OnboardingEvent()
    
    data object ConfirmSkip : OnboardingEvent()
    
    data object DismissSkipDialog : OnboardingEvent()
    
    data class ToggleCoin(val coinSymbol: String) : OnboardingEvent()
    
    data object ToggleNotifications : OnboardingEvent()
    
    data object CompleteOnboarding : OnboardingEvent()
    
    data object NavigateToMain : OnboardingEvent()
}
