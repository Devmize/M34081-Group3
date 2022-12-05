package com.itmo.microservices.demo.order.projection

import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = OrderAggregate::class, subscriberName = "demo-subs-stream"
)
class AnnotationBasedOrderEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedOrderEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: OrderCreatedEvent) {
        logger.info("order created successfully: orderId {}, items {}, date {}", event.id, event.itemsMap, event.timeCreated)
    }
}