package com.example.cryptowallet.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptowallet.theme.LocalCryptoColors

enum class BottomNavItem(
    val label: String,
    val icon: ImageVector
) {
    MARKET("Market", Icons.Default.Home),
    PORTFOLIO("Portfolio", Icons.Default.Star),
    DCA("DCA", Icons.Default.Refresh),
    COMPARE("Compare", Icons.Default.List),
    LEADERBOARD("Rank", Icons.Default.ThumbUp),
    ALERTS("Alerts", Icons.Default.Notifications)
}

@Composable
fun CryptoBottomNavigation(
    activeItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.cardBackground.copy(alpha = 0.95f))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem.entries.forEach { item ->
            BottomNavItemView(
                item = item,
                isActive = item == activeItem,
                onClick = { onItemSelected(item) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    val shape = RoundedCornerShape(12.dp)
    
    val backgroundModifier = if (isActive) {
        Modifier.background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    colors.accentBlue500.copy(alpha = 0.2f),
                    colors.accentPurple500.copy(alpha = 0.2f)
                )
            ),
            shape = shape
        )
    } else {
        Modifier.background(Color.Transparent, shape)
    }
    
    val iconTint = if (isActive) {
        colors.accentBlue400
    } else {
        colors.textSecondary
    }
    
    val textColor = if (isActive) {
        colors.textPrimary
    } else {
        colors.textSecondary
    }
    
    Column(
        modifier = modifier
            .clip(shape)
            .then(backgroundModifier)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 4.dp)
            .semantics { contentDescription = "Navigate to ${item.label}" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = item.label,
            fontSize = 10.sp,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            color = textColor,
            maxLines = 1
        )
    }
}
