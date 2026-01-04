/**
 * CoinClassification.kt
 *
 * Domain-level business logic for classifying cryptocurrencies.
 * Provides utilities for identifying coin types and price movement characteristics.
 *
 * This object encapsulates domain knowledge about:
 * - Stablecoin identification
 * - Price movement significance thresholds
 *
 * Used primarily for UI presentation logic (e.g., sparkline color determination)
 * but can be reused across Portfolio, Alerts, DCA, and other features.
 */
package com.example.valguard.app.coins.domain

import kotlin.math.abs

/**
 * Domain object for cryptocurrency classification logic.
 *
 * Provides business rules for identifying coin types and analyzing
 * price movements. This centralizes domain knowledge that may be
 * reused across multiple features.
 */
object CoinClassification {
    
    /**
     * Set of known stablecoin symbols.
     * Stablecoins are cryptocurrencies designed to maintain a stable value,
     * typically pegged to fiat currencies like USD.
     */
    private val STABLECOINS = setOf(
        "USDT",  // Tether USD
        "USDC",  // USD Coin
        "BUSD",  // Binance USD
        "DAI",   // Dai
        "TUSD",  // TrueUSD
        "USDD",  // USDD
        "USDP"   // Pax Dollar
    )
    
    /**
     * Threshold for determining minimal price movement (0.2%).
     * Movements at or below this threshold are considered insignificant
     * for visual emphasis purposes.
     */
    private const val MINIMAL_MOVEMENT_THRESHOLD = 0.2
    
    /**
     * Determines if a cryptocurrency is a stablecoin based on its symbol.
     *
     * Stablecoins are identified by their ticker symbol. The comparison
     * is case-insensitive to handle various symbol formats.
     *
     * @param symbol The cryptocurrency symbol (e.g., "USDT", "BTC")
     * @return true if the coin is a known stablecoin, false otherwise
     *
     * @example
     * ```kotlin
     * CoinClassification.isStablecoin("USDT") // true
     * CoinClassification.isStablecoin("usdc") // true
     * CoinClassification.isStablecoin("BTC")  // false
     * ```
     */
    fun isStablecoin(symbol: String): Boolean {
        return symbol.uppercase() in STABLECOINS
    }
    
    /**
     * Determines if a price change percentage represents minimal movement.
     *
     * Price movements at or below 0.2% (in either direction) are considered
     * minimal and may warrant different visual treatment to avoid
     * false emotional signals.
     *
     * @param changePercent The price change percentage (e.g., 0.15, -0.2, 1.5)
     * @return true if the absolute change is at or below 0.2%, false otherwise
     *
     * @example
     * ```kotlin
     * CoinClassification.isMinimalMovement(0.15)  // true
     * CoinClassification.isMinimalMovement(-0.2)  // true
     * CoinClassification.isMinimalMovement(0.2)   // true
     * CoinClassification.isMinimalMovement(0.5)   // false
     * CoinClassification.isMinimalMovement(-2.0)  // false
     * ```
     */
    fun isMinimalMovement(changePercent: Double): Boolean {
        return abs(changePercent) <= MINIMAL_MOVEMENT_THRESHOLD
    }
}
