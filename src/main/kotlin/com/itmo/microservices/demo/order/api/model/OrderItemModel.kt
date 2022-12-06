package com.itmo.microservices.demo.order.api.model

import java.util.*

class OrderItemModel(
    val id: UUID?,
    val title: String?,
    val price: Int?
)