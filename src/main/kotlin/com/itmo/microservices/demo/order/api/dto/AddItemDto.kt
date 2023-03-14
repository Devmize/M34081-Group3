package com.itmo.microservices.demo.order.api.dto

import java.util.*

data class AddItemDto(
    val orderId: UUID,
    val itemId: UUID,
    val amount: Int
)