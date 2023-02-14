package com.itmo.microservices.demo.catalog.logic

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.api.ItemAddedToCatalogEvent
import com.itmo.microservices.demo.catalog.api.ItemRemovedFromCatalogEvent
import com.itmo.microservices.demo.catalog.api.ProductPriceChangedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class CatalogAggregateState : AggregateState<String, CatalogAggregate> {
    private lateinit var catalogId: String
    var products = mutableMapOf<UUID, Product>()
    var orders = mutableMapOf<UUID, Order>()
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()

    override fun getId(): String {
        return this.catalogId
    }


    @StateTransitionFunc
    fun productPriceChangedApply(event: ProductPriceChangedEvent) {
        products[event.productId]?.price = event.price
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun itemAddedToCatalogApply(event: ItemAddedToCatalogEvent) {
        products[event.productId] =
            Product(event.productId, event.productName, event.price, event.count, event.description)
        updatedAt = createdAt
    }

    @StateTransitionFunc
    fun itemRemovedFromCatalogApply(event: ItemRemovedFromCatalogEvent) {
        products.remove(event.productId)
        updatedAt = createdAt
    }
}

data class Order(
    var orderId: UUID? = UUID.randomUUID(),
    var orderItems: MutableList<Product> = mutableListOf(),
    var price: Double? = 0.0,
    var date: Date? = Calendar.getInstance().time
)

data class Product(
    var productId: UUID? = UUID.randomUUID(),
    var productName: String? = null,
    var price: Double? = null,
    var count: Int? = null,
    var description: String? = null
)