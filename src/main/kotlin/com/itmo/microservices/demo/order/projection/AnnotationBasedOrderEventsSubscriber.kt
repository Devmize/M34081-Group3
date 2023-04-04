package com.itmo.microservices.demo.order.projection

import com.itmo.microservices.demo.order.api.OrderAddItemEvent
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.OrderBookedEvent
import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = OrderAggregate::class, subscriberName = "order-subs-stream"
)
class AnnotationBasedOrderEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedOrderEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: OrderCreatedEvent) {
        logger.info("order created successfully: orderId {}, items {}, timeCreated {}, status {}, deliveryDuration {}, paymentHistory {}", event.orderId, event.itemsMap,  event.timeCreated, event.status, event.deliveryDuration, event.paymentHistory)
    }

    @SubscribeEvent
    fun taskAddItemSubscriber(event: OrderAddItemEvent) {
        logger.info("item added into order successfully: orderId {}, itemId {}, amount {}", event.orderId, event.itemId, event.amount)
    }

    @SubscribeEvent
    fun taskBookedOrderSubscriber(event: OrderBookedEvent) {
        logger.info("order booked successfully: orderId {}", event.orderId)
    }
}