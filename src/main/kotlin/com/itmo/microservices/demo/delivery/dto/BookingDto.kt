package com.itmo.microservices.demo.delivery.dto

import java.util.*

data class BookingDto (
    val id: UUID,
    val failedItems: Set<UUID>
)