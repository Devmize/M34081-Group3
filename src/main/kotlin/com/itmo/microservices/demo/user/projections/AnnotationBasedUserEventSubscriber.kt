package com.itmo.microservices.demo.user.projections;

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(aggregateClass = UserAggregate::class, subscriberName = "user-logic-subs-stream")
class AnnotationBasedUserEventSubscriber {

    val logger: org.slf4j.Logger = LoggerFactory.getLogger(AnnotationBasedUserEventSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: UserCreatedEvent) {
        logger.info("user with id: {}, name: {} created successfully", event.id, event.name)
    }
}
