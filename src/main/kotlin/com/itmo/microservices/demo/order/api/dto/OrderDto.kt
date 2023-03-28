package com.itmo.microservices.demo.order.api.dto

import com.itmo.microservices.demo.order.api.model.OrderStatus
import java.util.*

data class OrderDto(
    val id: UUID,
    val status: OrderStatus,
    val itemsMap: Map<UUID, Int>,
    val timeCreated: Number,
    val deliveryDuration: Number,
    val paymentHistory: List<PaymentLogRecord>
)