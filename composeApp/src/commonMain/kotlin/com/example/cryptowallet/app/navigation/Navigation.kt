package com.example.cryptowallet.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.cryptowallet.app.coins.presentation.CoinsListScreen
import com.example.cryptowallet.app.portfolio.presentation.PortfolioScreen
import com.example.cryptowallet.app.trade.presentation.buy.BuyScreen
import com.example.cryptowallet.app.trade.presentation.sell.SellScreen


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavigationShell(navController = navController) { modifier ->
        NavHost(
            navController = navController,
            startDestination = Screens.Portfolio,
            modifier = modifier
        ) {
            composable<Screens.Portfolio> {
                PortfolioScreen(
                    onCoinItemClicked = { coinId ->
                        navController.navigate(Screens.Sell(coinId))
                    },
                    onDiscoverCoinsClicked = {
                        navController.navigate(Screens.Coins)
                    }
                )
            }
            composable<Screens.Coins> {
                CoinsListScreen(
                    onCoinClicked = { coinId ->
                        navController.navigate(Screens.Buy(coinId))
                    }
                )
            }
            composable<Screens.Sell> { backStackEntry ->
                val sell: Screens.Sell = backStackEntry.toRoute()
                SellScreen(
                    coinId = sell.coinId,
                    navigateToPortfolio = {
                        navController.navigate(Screens.Portfolio) {
                            popUpTo(Screens.Portfolio) { inclusive = true }
                        }
                    }
                )
            }
            composable<Screens.Buy> { backStackEntry ->
                val buy: Screens.Buy = backStackEntry.toRoute()
                BuyScreen(
                    coinId = buy.coinId,
                    navigateToPortfolio = {
                        navController.navigate(Screens.Portfolio) {
                            popUpTo(Screens.Portfolio) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
