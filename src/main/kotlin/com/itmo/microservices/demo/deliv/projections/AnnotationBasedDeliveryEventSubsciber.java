package com.itmo.microservices.demo.deliv.projections;

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate;
import org.springframework.stereotype.Service;
import ru.quipy.streams.annotation.AggregateSubscriber;

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate;
import com.itmo.microservices.demo.deliv.api.*;
import com.itmo.microservices.demo.deliv.api.DeliveryCompletedSuccessfullyEvent;
import com.itmo.microservices.demo.deliv.api.DeliveryFailedEvent;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.quipy.streams.annotation.AggregateSubscriber;
import ru.quipy.streams.annotation.SubscribeEvent;

@Service
@AggregateSubscriber(
        aggregateClass = DeliveryAggregate::class, subscriberName = "demo-subs-stream"
)
class AnnotationBasedDeliveryEventSubscriber {
    val logger: Logger = LoggerFactory.getLogger(AggregateSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: DeliveryAttemptEvent) {
        logger.info("attempt to delivery the order: time {}, phone {}, idnum {}",
                event.time, event.phoneNum, event.idnum)
    }
    @SubscribeEvent
    fun taskCreatedSubscriber(event: DeliveryCompletedSuccessfullyEvent) {
        logger.info("dalivery completed successfully: time {}, idnum {}",
                event.time, event.idnum)
    }

    @SubscribeEvent
    fun taskCreatedSubscriber(event: DeliveryFailedEvent) {
        logger.info("delivery failed: time {}, idnum {}",
                event.time, event.idnum)
    }
}