package com.example.cryptowallet.app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.cryptowallet.theme.LocalCryptoColors
import com.example.cryptowallet.theme.LocalCryptoSpacing
import com.example.cryptowallet.theme.LocalCryptoTypography

data class TradeConfirmation(
    val coinName: String,
    val coinSymbol: String,
    val amount: String,
    val price: String,
    val totalValue: String,
    val isBuy: Boolean
)

@Composable
fun TradeConfirmationDialog(
    confirmation: TradeConfirmation,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    val spacing = LocalCryptoSpacing.current
    
    val actionText = if (confirmation.isBuy) "Buy" else "Sell"
    val actionColor = if (confirmation.isBuy) colors.profit else colors.loss

    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = "Confirm $actionText",
                style = typography.titleLarge,
                color = colors.textPrimary
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = "Review your $actionText order: ${confirmation.amount} of ${confirmation.coinName} at ${confirmation.price} for a total of ${confirmation.totalValue}"
                    }
            ) {
                TradeSummaryRow(
                    label = "Coin",
                    value = "${confirmation.coinName} (${confirmation.coinSymbol})"
                )
                Spacer(modifier = Modifier.height(spacing.xs))
                TradeSummaryRow(
                    label = "Amount",
                    value = confirmation.amount
                )
                Spacer(modifier = Modifier.height(spacing.xs))
                TradeSummaryRow(
                    label = "Price",
                    value = confirmation.price
                )
                Spacer(modifier = Modifier.height(spacing.sm))
                TradeSummaryRow(
                    label = "Total",
                    value = confirmation.totalValue,
                    isHighlighted = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = actionColor
                )
            ) {
                Text(
                    text = "Confirm $actionText",
                    style = typography.labelLarge,
                    color = colors.cardBackground
                )
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text(
                    text = "Cancel",
                    style = typography.labelLarge,
                    color = colors.textSecondary
                )
            }
        }
    )
}

@Composable
private fun TradeSummaryRow(
    label: String,
    value: String,
    isHighlighted: Boolean = false
) {
    val colors = LocalCryptoColors.current
    val typography = LocalCryptoTypography.current
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = if (isHighlighted) typography.titleMedium else typography.bodyMedium,
            color = colors.textSecondary
        )
        Text(
            text = value,
            style = if (isHighlighted) typography.titleMedium else typography.bodyMedium,
            color = if (isHighlighted) colors.textPrimary else colors.textPrimary
        )
    }
}
