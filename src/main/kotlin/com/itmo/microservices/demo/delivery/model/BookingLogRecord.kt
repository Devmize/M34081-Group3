package com.itmo.microservices.demo.delivery.model

import java.util.*

enum class BookingStatus{
    FAILED, SUCCESS
}

data class BookingLogRecord(
    val bookingId: UUID,
    val itemId: UUID,
    val status: BookingStatus,
    val amount: Int,
    val timestamp: Long
)