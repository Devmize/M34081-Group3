package com.itmo.microservices.demo.catalog.logic

import com.itmo.microservices.demo.catalog.api.ItemAddedToCatalogEvent
import com.itmo.microservices.demo.catalog.api.ItemRemovedFromCatalogEvent
import com.itmo.microservices.demo.catalog.api.ProductPriceChangedEvent
import java.lang.IllegalArgumentException
import java.util.*

fun CatalogAggregateState.updateProductPrice(productId: UUID, price: Double): ProductPriceChangedEvent {
    if (price < 0) {
     throw IllegalArgumentException("price shouldn't be negative")
    }
    return ProductPriceChangedEvent(productId, price)
}

fun CatalogAggregateState.addItem(productId: UUID,
                                  productName: String,
                                  price: Double,
                                  count: Int,
                                  description: String): ItemAddedToCatalogEvent {

    return ItemAddedToCatalogEvent(
        productId,
        productName,
        price,
        count,
        description
    )
}

fun CatalogAggregateState.itemRemoved(productId: UUID): ItemRemovedFromCatalogEvent {
    if (products[productId] == null) {
        throw IllegalArgumentException("element with id ${productId} not exists")
    }
    return ItemRemovedFromCatalogEvent(productId)
}