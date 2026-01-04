package com.example.valguard.app.components

import com.example.valguard.app.coindetail.domain.ChartTimeframe
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Generates evenly distributed X-axis labels for chart display.
 * Labels are formatted based on the selected timeframe for optimal readability.
 */
object XAxisLabelGenerator {
    
    /**
     * Generates X-axis labels from chart points based on the selected timeframe.
     * 
     * @param points List of chart points with timestamps
     * @param timeframe The selected chart timeframe
     * @param labelCount Target number of labels (default 4, will be reduced for small datasets)
     * @return List of formatted label strings
     */
    fun generate(
        points: List<ChartPoint>,
        timeframe: ChartTimeframe,
        labelCount: Int = 4
    ): List<String> {
        if (points.isEmpty()) return emptyList()
        
        // Guard against small datasets - never generate more labels than data points
        val safeLabelCount = minOf(labelCount, points.size)
        val divisor = (safeLabelCount - 1).coerceAtLeast(1)
        
        return (0 until safeLabelCount).mapIndexed { i, _ ->
            val index = (i * points.lastIndex) / divisor
            val timestamp = points[index].timestamp
            
            val label = formatTimestamp(timestamp, timeframe)
            
            // Prefix first label with "Since" for ALL timeframe for clarity
            if (i == 0 && timeframe == ChartTimeframe.ALL) "Since $label" else label
        }
    }
    
    /**
     * Formats a timestamp according to the timeframe's display requirements.
     * Handles both seconds and milliseconds timestamps.
     */
    private fun formatTimestamp(timestamp: Long, timeframe: ChartTimeframe): String {
        // API typically returns timestamps in seconds, convert to milliseconds if needed
        val timestampMillis = if (timestamp < 10_000_000_000L) timestamp * 1000 else timestamp
        
        val instant = Instant.fromEpochMilliseconds(timestampMillis)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        
        return when (timeframe) {
            // 24H: "2 AM", "8 PM" format
            ChartTimeframe.DAY_1 -> {
                val hour = localDateTime.hour
                val displayHour = when {
                    hour == 0 -> 12
                    hour > 12 -> hour - 12
                    else -> hour
                }
                val amPm = if (hour < 12) "AM" else "PM"
                "$displayHour $amPm"
            }
            
            // 7D: "Mon", "Wed", "Fri" format
            ChartTimeframe.WEEK_1 -> {
                val dayOfWeek = localDateTime.dayOfWeek.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() }
                dayOfWeek
            }
            
            // 1M: "Jan 5", "Jan 12" format
            ChartTimeframe.MONTH_1 -> {
                val month = localDateTime.month.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() }
                val day = localDateTime.dayOfMonth
                "$month $day"
            }
            
            // 3M: "Jan", "Feb", "Mar" format
            ChartTimeframe.MONTH_3 -> {
                localDateTime.month.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() }
            }
            
            // 1Y: "Jan", "Apr", "Jul", "Oct" format (quarterly)
            ChartTimeframe.YEAR_1 -> {
                localDateTime.month.name.take(3).lowercase()
                    .replaceFirstChar { it.uppercase() }
            }
            
            // ALL: "2019", "2020", "2021" format
            ChartTimeframe.ALL -> {
                localDateTime.year.toString()
            }
        }
    }
}
