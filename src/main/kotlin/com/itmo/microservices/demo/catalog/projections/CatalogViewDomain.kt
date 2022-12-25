package com.itmo.microservices.demo.catalog.projections

import com.itmo.microservices.demo.catalog.logic.Order
import com.itmo.microservices.demo.catalog.logic.Product
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class CatalogViewDomain {
    @Document("catalog-view")
    data class Catalog(
        val catalogId: String,
        val products: Map<UUID, Product>,
        val order: Map<UUID, Order>
    )
}