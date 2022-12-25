package com.itmo.microservices.demo.deliv.projections;

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.quipy.streams.annotation.AggregateSubscriber;
import ru.quipy.streams.annotation.SubscribeEvent;

@Service
@AggregateSubscriber(aggregateClass = DeliveryAggregate::class, subscriberName = "user-subs-stream")
class AnnotationBasedDeliveryEventSubscriber {
    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedDeliveryEventSubscriber::class.java)

//    @SubscribeEvent
//    fun deliveryCreatedSubscriber(event:DeliveryCreatedEvent) {
//        logger.info("delivery created successfully: deliveryId {}, TimeStamp {}, phoneNumber{}", event.orderId, event.timeStamp, event.phoneNumber)
//    }
//
//    @SubscribeEvent
//    fun deliveryEndedSubscriber(event:deliveryEndedEvent) {
//        logger.info("delivery ended successfully: deliveryId {}, TimeStamp {}, phoneNumber{}", event.orderId, event.timeStamp, event.phoneNumber)
//    }
}