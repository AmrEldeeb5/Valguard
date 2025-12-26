package com.example.cryptowallet.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptowallet.theme.CryptoGradients
import com.example.cryptowallet.theme.LocalCryptoColors

@Composable
fun CryptoVaultHeader(
    alertCount: Int,
    onAlertClick: () -> Unit,
    onMoreClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val colors = LocalCryptoColors.current
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            // Gradient title
            Text(
                text = "CryptoVault",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = androidx.compose.ui.text.TextStyle(
                    brush = CryptoGradients.brandGradient()
                )
            )
            Text(
                text = "Track your crypto portfolio",
                fontSize = 14.sp,
                color = colors.textSecondary
            )
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Alert bell with badge
            Box {
                IconButton(
                    onClick = onAlertClick,
                    modifier = Modifier.semantics { contentDescription = "Open alerts" }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Alerts",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                // Badge
                if (alertCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-4).dp, y = 4.dp)
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        colors.accentBlue500,
                                        colors.accentPurple500
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (alertCount > 9) "9+" else alertCount.toString(),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.textPrimary
                        )
                    }
                }
            }
            
            // More menu button
            if (onMoreClick != null) {
                IconButton(
                    onClick = onMoreClick,
                    modifier = Modifier.semantics { contentDescription = "Open menu" }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = colors.textSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
