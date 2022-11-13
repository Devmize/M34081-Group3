package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class PaymentEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(PaymentEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(PaymentAggregate::class, "some-meaningful-name") {

            `when`(PaymentAttemptEvent::class) { event ->
                logger.info("attempt to pay the order: paymentId {}, orderId {}, sum {}, status {}",
                    event.paymentId, event.orderId, event.sum, event.status)
            }
        }
    }
}