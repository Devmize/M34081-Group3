package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import com.sun.org.slf4j.internal.Logger
import com.sun.org.slf4j.internal.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import kotlin.math.log

@Service
@AggregateSubscriber(
    aggregateClass = PaymentAggregate::class, subscriberName = "demo-subs-stream"
)
class AnnotationBasedPaymentEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedPaymentEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: PaymentAttemptEvent) {
        logger.info("attempt to pay the order: paymentId {}, orderId {}, sum {}, status {}",
            event.paymentId, event.orderId, event.sum, event.status)
    }

}