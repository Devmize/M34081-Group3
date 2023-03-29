package com.itmo.microservices.demo.catalog.model

import com.itmo.microservices.demo.catalog.logic.Product
import java.util.*

data class CatalogItemDto(
    val id: UUID,
    val title: String,
    val description: String,
    val price: Double,
    val amount: Int
)
