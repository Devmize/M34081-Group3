package com.itmo.microservices.demo.catalog.model

import java.util.*

data class AddItemRequest(
    val catalogId: String,
    val productId: UUID,
    val productName: String,
    val price: Double,
    val count: Int,
    val description: String
)