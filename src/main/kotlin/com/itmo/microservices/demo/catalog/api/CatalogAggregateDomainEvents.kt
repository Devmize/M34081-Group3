package com.itmo.microservices.demo.catalog.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PRODUCT_PRICE_CHANGED_EVENT: String = "PRODUCT_PRICE_CHANGED_EVENT"
const val ITEM_ADDED_TO_CATALOG_EVENT: String = "ITEM_ADDED_TO_CATALOG_EVENT"
const val ITEM_REMOVED_FROM_CATALOG_EVENT: String = "ITEM_REMOVED_FROM_CATALOG_EVENT"

@DomainEvent(name = PRODUCT_PRICE_CHANGED_EVENT)
class ProductPriceChangedEvent(
    val productId: UUID,
    val price: Double,
    createdAt: Long = System.currentTimeMillis(),
) : Event<CatalogAggregate>(
    name = PRODUCT_PRICE_CHANGED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ITEM_ADDED_TO_CATALOG_EVENT)
class ItemAddedToCatalogEvent(
    val productId: UUID,
    val productName: String,
    val price: Double,
    val count: Int,
    var description: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<CatalogAggregate>(
    name = ITEM_ADDED_TO_CATALOG_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ITEM_REMOVED_FROM_CATALOG_EVENT)
class ItemRemovedFromCatalogEvent(
    val productId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<CatalogAggregate>(
    name = ITEM_REMOVED_FROM_CATALOG_EVENT,
    createdAt = createdAt
)