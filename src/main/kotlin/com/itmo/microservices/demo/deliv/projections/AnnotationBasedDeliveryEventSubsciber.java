package com.itmo.microservices.demo.deliv.projections;

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate;
import org.springframework.stereotype.Service;
import ru.quipy.streams.annotation.AggregateSubscriber;

@Service
@AggregateSubscriber(aggregateClass = DeliveryAggregate.class, subscriberName = "user-subs-stream")
public class AnnotationBasedDeliveryEventSubscriber {
    val logger: Logger = LoggerFactory.getLogger(AggregateSubscriber::class.java)

    @SubscribeEvent
    fun deliveryCreatedSubscriber(event: deliveryCreatedEvent) {
        logger.info("delivery created successfully: deliveryId {}, TimeStamp {}, phoneNumber{}", event.orderId, event.timeStamp, event.phoneNumber)
    }

    @SubscribeEvent
    fun deliveryEndedSubscriber(event: deliveryEndedEvent) {
        logger.info("delivery ended successfully: deliveryId {}, TimeStamp {}, phoneNumber{}", event.orderId, event.timeStamp, event.phoneNumber)
    }
}