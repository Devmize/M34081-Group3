package com.itmo.microservices.demo.order.api.dto

import java.util.UUID

data class BookedDto (
    val orderId: UUID
)