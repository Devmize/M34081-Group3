package com.itmo.microservices.demo.delivery.projections

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import com.itmo.microservices.demo.delivery.api.DeliveryAttemptEvent
import com.itmo.microservices.demo.delivery.api.DeliveryCompletedSuccessfullyEvent
import com.itmo.microservices.demo.delivery.api.DeliveryFailedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = DeliveryAggregate::class, subscriberName = "delivery-subs-stream"
)
class AnnotationBasedDeliveryEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedDeliveryEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: DeliveryAttemptEvent) {
        logger.info("attempt to deliv the order: paymentId {}, orderId {}, sum {}, status {}",
            event.deliveryId, event.address, event.phoneNumber, event.timestamp)
    }
    @SubscribeEvent
    fun taskCreatedSubscriber(event: DeliveryCompletedSuccessfullyEvent) {
        logger.info("delivery completed successfully: paymentId {}, orderId {}, sum {}, status {}",
            event.deliveryId, event.address, event.phoneNumber, event.timestamp)
    }

    @SubscribeEvent
    fun taskCreatedSubscriber(event: DeliveryFailedEvent) {
        logger.info("delivery failed: paymentId {}, orderId {}, sum {}, status {}",
            event.deliveryId, event.address, event.phoneNumber, event.timestamp)
    }
}