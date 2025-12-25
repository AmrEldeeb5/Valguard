package com.example.cryptowallet.app.core.database.portfolio

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptowallet.app.compare.data.local.SavedComparisonDao
import com.example.cryptowallet.app.compare.data.local.SavedComparisonEntity
import com.example.cryptowallet.app.dca.data.local.DCAExecutionDao
import com.example.cryptowallet.app.dca.data.local.DCAExecutionEntity
import com.example.cryptowallet.app.dca.data.local.DCAScheduleDao
import com.example.cryptowallet.app.dca.data.local.DCAScheduleEntity
import com.example.cryptowallet.app.portfolio.data.local.PortfolioCoinEntity
import com.example.cryptowallet.app.portfolio.data.local.PortfolioDao
import com.example.cryptowallet.app.portfolio.data.local.UserBalanceDao
import com.example.cryptowallet.app.portfolio.data.local.UserBalanceEntity
import com.example.cryptowallet.app.watchlist.data.local.WatchlistDao
import com.example.cryptowallet.app.watchlist.data.local.WatchlistEntity


@Database(
    entities = [
        PortfolioCoinEntity::class,
        UserBalanceEntity::class,
        WatchlistEntity::class,
        DCAScheduleEntity::class,
        DCAExecutionEntity::class,
        SavedComparisonEntity::class
    ],
    version = 5
)
abstract class PortfolioDatabase: RoomDatabase() {
    abstract fun portfolioDao(): PortfolioDao
    abstract fun UserBalanceDao(): UserBalanceDao
    abstract fun watchlistDao(): WatchlistDao
    abstract fun dcaScheduleDao(): DCAScheduleDao
    abstract fun dcaExecutionDao(): DCAExecutionDao
    abstract fun savedComparisonDao(): SavedComparisonDao
}