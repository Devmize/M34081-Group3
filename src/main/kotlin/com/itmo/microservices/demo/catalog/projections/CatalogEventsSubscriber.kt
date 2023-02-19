package com.itmo.microservices.demo.catalog.projections

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.api.ItemAddedToCatalogEvent
import com.itmo.microservices.demo.catalog.api.ItemRemovedFromCatalogEvent
import com.itmo.microservices.demo.catalog.api.ProductPriceChangedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class CatalogEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(CatalogEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(CatalogAggregate::class, "catalog") {

            `when`(ProductPriceChangedEvent::class) { event ->
                logger.info("productPriceChanged: productId {}, new price {}", event.productId, event.price)
            }

            `when`(ItemAddedToCatalogEvent::class) { event ->
                logger.info("Item Added: {}", event.productId)
            }

            `when`(ItemRemovedFromCatalogEvent::class) { event ->
                logger.info("Item removed {}: ", event.productId)
            }
        }
    }
}