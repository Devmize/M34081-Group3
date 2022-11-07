package com.itmo.microservices.demo.delivery.projections

import com.itmo.microservices.demo.delivery.api.UserAggregate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class DeliveryEventSubscriber {

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(DeliveryAggregate::class, "some-subscriber-name") {
            Logger.debug("subscriber created for user")
        }
    }
    val Logger = LoggerFactory.getLogger(DeliveryEventSubscriber::class.java)

    lateinit var subscriptionsManager: AggregateSubscriptionsManager

}