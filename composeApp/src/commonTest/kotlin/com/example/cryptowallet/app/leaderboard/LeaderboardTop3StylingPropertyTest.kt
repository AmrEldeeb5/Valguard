package com.example.cryptowallet.app.leaderboard

import com.example.cryptowallet.app.leaderboard.presentation.LeaderboardCalculator
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LeaderboardTop3StylingPropertyTest {
    
    @Test
    fun `rank 1 gets gold trophy badge`() {
        val badge = LeaderboardCalculator.getRankBadge(1)
        
        assertNotNull(badge, "Rank 1 should have a badge")
        assertEquals("🏆", badge, "Rank 1 should get gold trophy")
    }
    
    @Test
    fun `rank 2 gets silver medal badge`() {
        val badge = LeaderboardCalculator.getRankBadge(2)
        
        assertNotNull(badge, "Rank 2 should have a badge")
        assertEquals("🥈", badge, "Rank 2 should get silver medal")
    }
    
    @Test
    fun `rank 3 gets bronze medal badge`() {
        val badge = LeaderboardCalculator.getRankBadge(3)
        
        assertNotNull(badge, "Rank 3 should have a badge")
        assertEquals("🥉", badge, "Rank 3 should get bronze medal")
    }
    
    @Test
    fun `ranks 4 and above get no badge`() {
        (4..100).forEach { rank ->
            val badge = LeaderboardCalculator.getRankBadge(rank)
            
            assertNull(badge, "Rank $rank should not have a badge")
        }
    }
    
    @Test
    fun `isTopThree returns true for ranks 1 to 3`() {
        assertTrue(LeaderboardCalculator.isTopThree(1), "Rank 1 should be top three")
        assertTrue(LeaderboardCalculator.isTopThree(2), "Rank 2 should be top three")
        assertTrue(LeaderboardCalculator.isTopThree(3), "Rank 3 should be top three")
    }
    
    @Test
    fun `isTopThree returns false for ranks 4 and above`() {
        (4..100).forEach { rank ->
            assertFalse(LeaderboardCalculator.isTopThree(rank), "Rank $rank should not be top three")
        }
    }
    
    @Test
    fun `isTopThree returns false for zero and negative ranks`() {
        assertFalse(LeaderboardCalculator.isTopThree(0), "Rank 0 should not be top three")
        assertFalse(LeaderboardCalculator.isTopThree(-1), "Negative rank should not be top three")
        assertFalse(LeaderboardCalculator.isTopThree(-100), "Negative rank should not be top three")
    }
    
    @Test
    fun `zero and negative ranks get no badge`() {
        assertNull(LeaderboardCalculator.getRankBadge(0), "Rank 0 should not have a badge")
        assertNull(LeaderboardCalculator.getRankBadge(-1), "Negative rank should not have a badge")
    }
    
    @Test
    fun `all top 3 badges are unique`() {
        val badges = listOf(
            LeaderboardCalculator.getRankBadge(1),
            LeaderboardCalculator.getRankBadge(2),
            LeaderboardCalculator.getRankBadge(3)
        ).filterNotNull()
        
        assertEquals(3, badges.size, "All top 3 should have badges")
        assertEquals(3, badges.toSet().size, "All badges should be unique")
    }
    
    @Test
    fun `badges are emoji characters`() {
        val badges = listOf(
            LeaderboardCalculator.getRankBadge(1),
            LeaderboardCalculator.getRankBadge(2),
            LeaderboardCalculator.getRankBadge(3)
        ).filterNotNull()
        
        badges.forEach { badge ->
            assertTrue(badge.isNotEmpty(), "Badge should not be empty")
            // Emoji characters are typically in supplementary planes or specific ranges
            assertTrue(badge.length <= 2, "Badge should be a single emoji")
        }
    }
    
    @Test
    fun `isTopThree and getRankBadge are consistent`() {
        (1..100).forEach { rank ->
            val isTop = LeaderboardCalculator.isTopThree(rank)
            val badge = LeaderboardCalculator.getRankBadge(rank)
            
            if (isTop) {
                assertNotNull(badge, "Top three rank $rank should have a badge")
            } else {
                assertNull(badge, "Non-top-three rank $rank should not have a badge")
            }
        }
    }
    
    @Test
    fun `badge order reflects rank hierarchy`() {
        // Trophy > Silver > Bronze in terms of prestige
        val badge1 = LeaderboardCalculator.getRankBadge(1)
        val badge2 = LeaderboardCalculator.getRankBadge(2)
        val badge3 = LeaderboardCalculator.getRankBadge(3)
        
        // All should be different
        assertTrue(badge1 != badge2, "Rank 1 and 2 badges should differ")
        assertTrue(badge2 != badge3, "Rank 2 and 3 badges should differ")
        assertTrue(badge1 != badge3, "Rank 1 and 3 badges should differ")
    }
    
    @Test
    fun `large rank numbers are handled correctly`() {
        val largeRanks = listOf(100, 1000, 10000, Int.MAX_VALUE)
        
        largeRanks.forEach { rank ->
            assertFalse(LeaderboardCalculator.isTopThree(rank), "Large rank $rank should not be top three")
            assertNull(LeaderboardCalculator.getRankBadge(rank), "Large rank $rank should not have badge")
        }
    }
}
