package com.itmo.microservices.demo.order.api.dto

import com.itmo.microservices.demo.order.api.model.OrderStatus
import java.util.*

data class OrderDto(
    val id: UUID,
    val timeCreated: Number,
    val status: OrderStatus,
    val itemsMap: Map<UUID, Double>,
    val deliveryDuration: Number,
    val paymentHistory: List<PaymentLogRecord>
)