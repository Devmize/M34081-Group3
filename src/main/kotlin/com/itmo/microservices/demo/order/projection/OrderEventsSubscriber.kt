package com.itmo.microservices.demo.order.projection

import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class OrderEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(OrderEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(OrderAggregate::class, "order") {

            `when`(OrderCreatedEvent::class) { event ->
                logger.info("order created successfully: orderId {}, items {}, date {}", event.id, event.itemsMap,  event.timeCreated)
            }
        }
    }
}