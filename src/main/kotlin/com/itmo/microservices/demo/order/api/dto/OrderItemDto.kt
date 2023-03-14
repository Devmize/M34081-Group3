package com.itmo.microservices.demo.order.api.dto

import java.util.*

data class OrderItemDto(
    val id: UUID,
    val title: String,
    val price: Int
)