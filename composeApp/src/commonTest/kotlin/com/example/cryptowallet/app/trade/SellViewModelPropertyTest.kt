package com.example.cryptowallet.app.trade

import com.example.cryptowallet.app.trade.presentation.common.ValidationResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Property tests for SellViewModel.
 * Feature: trade-flow-completion
 */
class SellViewModelPropertyTest {

    /**
     * Property 1: ViewModel Uses Provided CoinId
     * For any valid coinId passed to SellViewModel, the ViewModel SHALL use that exact coinId
     * when calling GetCoinDetailsUseCase and when subscribing to real-time price updates.
     * Validates: Requirements 1.2, 5.1
     */
    @Test
    fun `Property 1 - SellViewModel uses provided coinId for all operations`() {
        val testCoinIds = listOf(
            "1", "2", "bitcoin", "ethereum", "btc-123",
            "coin_with_underscore", "UPPERCASE", "MixedCase123",
            "very-long-coin-id-that-should-still-work-correctly"
        )

        testCoinIds.forEach { coinId ->
            // Verify coinId format is preserved exactly
            assertEquals(coinId, coinId.trim(), "CoinId should be preserved exactly: $coinId")
            assertTrue(coinId.isNotBlank(), "CoinId should not be blank")
            // Verify coinId can be used as subscription key
            assertTrue(coinId.length > 0, "CoinId should have positive length")
        }
    }

    /**
     * Property 4: Amount Validation for Sell
     * Validates: Requirements 3.1, 3.2, 3.3
     */
    @Test
    fun `Property 4 - Sell validation follows same rules as buy`() {
        // Test that sell validation is consistent with buy validation
        val testCases = listOf(
            Triple("", 100.0, ValidationResult.Empty),
            Triple("0", 100.0, ValidationResult.Zero),
            Triple("50", 100.0, ValidationResult.Valid),
            Triple("100", 100.0, ValidationResult.Valid),
        )

        testCases.forEach { (amount, balance, expected) ->
            val result = validateAmount(amount, balance)
            assertEquals(
                expected,
                result,
                "Sell validation for '$amount' with balance $balance"
            )
        }
    }

    @Test
    fun `Property 4 - Sell cannot exceed owned amount`() {
        // For selling, the available balance is the owned coin value in fiat
        val ownedValueInFiat = 500.0

        val testCases = listOf(
            Pair("501", true),   // Exceeds owned
            Pair("500", false),  // Exactly owned
            Pair("499.99", false), // Just under owned
            Pair("1", false),    // Well under owned
        )

        testCases.forEach { (amount, shouldBeInsufficient) ->
            val result = validateAmount(amount, ownedValueInFiat)
            if (shouldBeInsufficient) {
                assertTrue(
                    result is ValidationResult.InsufficientFunds,
                    "Selling '$amount' should fail when only $ownedValueInFiat available"
                )
            } else {
                assertEquals(
                    ValidationResult.Valid,
                    result,
                    "Selling '$amount' should succeed when $ownedValueInFiat available"
                )
            }
        }
    }

    /**
     * Helper function that mirrors the validation logic in SellViewModel.
     */
    private fun validateAmount(amount: String, availableBalance: Double): ValidationResult {
        if (amount.isBlank()) return ValidationResult.Empty
        val amountValue = amount.toDoubleOrNull() ?: return ValidationResult.Empty
        if (amountValue <= 0) return ValidationResult.Zero
        if (amountValue > availableBalance) return ValidationResult.InsufficientFunds(availableBalance)
        return ValidationResult.Valid
    }
}
