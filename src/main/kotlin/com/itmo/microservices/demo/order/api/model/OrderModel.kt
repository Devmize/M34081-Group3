package com.itmo.microservices.demo.order.api.model

import java.util.*

data class OrderModel(
    val id: UUID?,
    val timeCreated: Long?,
    val status: OrderStatus?,
    val itemsMap: Map<UUID, Amount>?,
    val deliveryDuration: Int?,
)
