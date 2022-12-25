
package com.itmo.microservices.demo.delivery.projections

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate
import com.itmo.microservices.demo.deliv.api.DeliveryAttemptEvent
import com.itmo.microservices.demo.deliv.api.DeliveryCompletedSuccessfullyEvent
import com.itmo.microservices.demo.deliv.api.DeliveryFailedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class DeliveryEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(DeliveryEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(DeliveryAggregate::class, "some-meaningful-name") {

            `when`(DeliveryAttemptEvent::class) { event ->
                logger.info("attempt to pay the order: paymentId {}, orderId {}, sum {}, status {}",
                    event.deliveryId, event.address, event.phoneNumber, event.timestamp)
            }
            `when`(DeliveryCompletedSuccessfullyEvent::class) { event ->
                logger.info("payment completed successfully: paymentId {}, orderId {}, sum {}, status {}",
                    event.deliveryId, event.address, event.phoneNumber, event.timestamp)
            }
            `when`(DeliveryFailedEvent::class) { event ->
                logger.info("payment failed: paymentId {}, orderId {}, sum {}, status {}",
                    event.deliveryId, event.address, event.phoneNumber, event.timestamp)
            }
        }
    }
}