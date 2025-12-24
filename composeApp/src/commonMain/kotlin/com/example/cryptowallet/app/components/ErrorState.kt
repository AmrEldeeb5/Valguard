package com.example.cryptowallet.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoSpacing
import com.example.cryptowallet.theme.LocalCryptoTypography

/**
 * A component for displaying error states with optional retry functionality.
 * 
 * @param message The error message to display
 * @param onRetry Optional callback for retry action. If provided, a retry button is shown.
 * @param modifier Modifier for the component
 */
@Composable
fun ErrorState(
    message: String,
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val spacing = LocalCryptoSpacing.current
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(spacing.lg)
            .semantics {
                contentDescription = "Error: $message"
            }
    ) {
        // Error icon (using emoji for simplicity, could be replaced with vector icon)
        Text(
            text = "⚠️",
            style = typography.displayMedium,
            modifier = Modifier.size(48.dp)
        )
        
        Spacer(modifier = Modifier.height(spacing.md))
        
        // Error message
        Text(
            text = message,
            style = typography.bodyLarge,
            color = colors.statusError,
            textAlign = TextAlign.Center
        )
        
        // Retry button (only shown if onRetry is provided)
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(spacing.lg))
            
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.buttonPrimary
                )
            ) {
                Text(
                    text = "Try Again",
                    style = typography.labelLarge
                )
            }
        }
    }
}
