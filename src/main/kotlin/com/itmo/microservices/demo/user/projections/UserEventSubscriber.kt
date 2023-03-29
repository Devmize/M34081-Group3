package com.itmo.microservices.demo.user.projections

import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class UserEventSubscriber {

    val logger: Logger = LoggerFactory.getLogger(UserEventSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "user") {


            `when`(UserCreatedEvent::class) { event ->
                logger.info("user created", event.userId, event.userName)
            }
        }
    }
}