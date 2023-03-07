package com.itmo.microservices.demo.catalog.projections

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.api.ItemAddedToCatalogEvent
import com.itmo.microservices.demo.catalog.api.ItemRemovedFromCatalogEvent
import com.itmo.microservices.demo.catalog.api.ProductPriceChangedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = CatalogAggregate::class, subscriberName = "catalog-subs-stream"
)
class AnnotationBasedCatalogEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedCatalogEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: ProductPriceChangedEvent) {
        logger.info("productPriceChanged: productId {}, new price {}", event.productId, event.price)
    }

    @SubscribeEvent
    fun taskCreatedSubscriber(event: ItemAddedToCatalogEvent) {
        logger.info("Item Added: {}", event.productId)
    }

    @SubscribeEvent
    fun taskCreatedSubscriber(event: ItemRemovedFromCatalogEvent) {
        logger.info("Item removed {}: ", event.productId)
    }
}