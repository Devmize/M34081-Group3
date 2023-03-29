package com.itmo.microservices.demo.user.projections;

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(aggregateClass = UserAggregate::class, subscriberName = "user-subs-stream")
class AnnotationBasedUserEventSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedUserEventSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: UserCreatedEvent) {
        logger.info("user with id: {}, name: {} created successfully", event.userId, event.userName)
    }
}
