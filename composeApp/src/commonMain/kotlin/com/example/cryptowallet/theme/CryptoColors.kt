package com.example.cryptowallet.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CryptoColors(
    // Background colors
    val backgroundPrimary: Color,
    val backgroundSecondary: Color,
    
    // Profit/Loss semantic colors
    val profit: Color,
    val loss: Color,
    val neutral: Color,
    
    // Card and surface colors
    val cardBackground: Color,
    val cardBackgroundElevated: Color,
    val cardBorder: Color,
    
    // Text colors
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    
    // Accent colors - Blue
    val accentBlue400: Color,
    val accentBlue500: Color,
    val accentBlue600: Color,
    
    // Accent colors - Purple
    val accentPurple400: Color,
    val accentPurple500: Color,
    val accentPurple600: Color,
    
    // Accent colors - Pink
    val accentPink400: Color,
    val accentPink500: Color,
    
    // Status colors
    val statusConnected: Color,
    val statusConnecting: Color,
    val statusError: Color,
    
    // Interactive element colors
    val buttonPrimary: Color,
    val buttonSecondary: Color,
    val buttonDisabled: Color,
    
    // Divider and border colors
    val divider: Color,
    val border: Color,
    
    // Shimmer/skeleton colors
    val shimmerBase: Color,
    val shimmerHighlight: Color
)

// CryptoVault Dark Theme - Primary theme
val DarkCryptoColors = CryptoColors(
    // Backgrounds - Slate 950/900
    backgroundPrimary = Color(0xFF020617),    // slate-950
    backgroundSecondary = Color(0xFF0F172A),  // slate-900
    
    // Profit/Loss - Emerald/Rose
    profit = Color(0xFF34D399),               // emerald-400
    loss = Color(0xFFFB7185),                 // rose-400
    neutral = Color(0xFF94A3B8),              // slate-400
    
    // Cards - Slate 800 with transparency
    cardBackground = Color(0xFF1E293B),       // slate-800
    cardBackgroundElevated = Color(0xFF334155), // slate-700
    cardBorder = Color(0xFF475569),           // slate-600
    
    // Text
    textPrimary = Color(0xFFFFFFFF),          // white
    textSecondary = Color(0xFFCBD5E1),        // slate-300 (lighter for better contrast)
    textTertiary = Color(0xFF94A3B8),         // slate-400
    
    // Accent - Blue
    accentBlue400 = Color(0xFF60A5FA),        // blue-400
    accentBlue500 = Color(0xFF3B82F6),        // blue-500
    accentBlue600 = Color(0xFF2563EB),        // blue-600
    
    // Accent - Purple
    accentPurple400 = Color(0xFFC084FC),      // purple-400
    accentPurple500 = Color(0xFFA855F7),      // purple-500
    accentPurple600 = Color(0xFF9333EA),      // purple-600
    
    // Accent - Pink
    accentPink400 = Color(0xFFF472B6),        // pink-400
    accentPink500 = Color(0xFFEC4899),        // pink-500
    
    // Status
    statusConnected = Color(0xFF34D399),      // emerald-400
    statusConnecting = Color(0xFF60A5FA),     // blue-400
    statusError = Color(0xFFFB7185),          // rose-400
    
    // Buttons - Use darker blue for better contrast with white text
    buttonPrimary = Color(0xFF1D4ED8),        // blue-700 (darker for contrast)
    buttonSecondary = Color(0xFF1E293B),      // slate-800
    buttonDisabled = Color(0xFF475569),       // slate-600
    
    // Dividers
    divider = Color(0xFF334155),              // slate-700
    border = Color(0xFF475569),               // slate-600
    
    // Shimmer
    shimmerBase = Color(0xFF1E293B),          // slate-800
    shimmerHighlight = Color(0xFF334155)      // slate-700
)

// Light theme - kept for compatibility but styled similarly
val LightCryptoColors = CryptoColors(
    // Backgrounds - Using lighter slate tones
    backgroundPrimary = Color(0xFFF8FAFC),    // slate-50
    backgroundSecondary = Color(0xFFF1F5F9),  // slate-100
    
    // Profit/Loss
    profit = Color(0xFF059669),               // emerald-600 (darker for light bg)
    loss = Color(0xFFE11D48),                 // rose-600
    neutral = Color(0xFF64748B),              // slate-500
    
    // Cards
    cardBackground = Color(0xFFFFFFFF),       // white
    cardBackgroundElevated = Color(0xFFF1F5F9), // slate-100
    cardBorder = Color(0xFFE2E8F0),           // slate-200
    
    // Text
    textPrimary = Color(0xFF0F172A),          // slate-900
    textSecondary = Color(0xFF475569),        // slate-600 (darker for better contrast)
    textTertiary = Color(0xFF64748B),         // slate-500
    
    // Accent - Blue
    accentBlue400 = Color(0xFF60A5FA),        // blue-400
    accentBlue500 = Color(0xFF3B82F6),        // blue-500
    accentBlue600 = Color(0xFF2563EB),        // blue-600
    
    // Accent - Purple
    accentPurple400 = Color(0xFFC084FC),      // purple-400
    accentPurple500 = Color(0xFFA855F7),      // purple-500
    accentPurple600 = Color(0xFF9333EA),      // purple-600
    
    // Accent - Pink
    accentPink400 = Color(0xFFF472B6),        // pink-400
    accentPink500 = Color(0xFFEC4899),        // pink-500
    
    // Status
    statusConnected = Color(0xFF059669),      // emerald-600
    statusConnecting = Color(0xFF3B82F6),     // blue-500
    statusError = Color(0xFFE11D48),          // rose-600
    
    // Buttons - Use darker blue for better contrast with white text
    buttonPrimary = Color(0xFF1D4ED8),        // blue-700 (darker for contrast)
    buttonSecondary = Color(0xFFE2E8F0),      // slate-200
    buttonDisabled = Color(0xFFCBD5E1),       // slate-300
    
    // Dividers
    divider = Color(0xFFE2E8F0),              // slate-200
    border = Color(0xFFCBD5E1),               // slate-300
    
    // Shimmer
    shimmerBase = Color(0xFFE2E8F0),          // slate-200
    shimmerHighlight = Color(0xFFF1F5F9)      // slate-100
)

val LocalCryptoColors = compositionLocalOf { DarkCryptoColors }
