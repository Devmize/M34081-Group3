package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import com.itmo.microservices.demo.payment.api.PaymentCompletedSuccessfullyEvent
import com.itmo.microservices.demo.payment.api.PaymentFailedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Service
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = PaymentAggregate::class, subscriberName = "payment-subs-stream"
)
class AnnotationBasedPaymentEventsSubscriber {

    val logger: Logger = LoggerFactory.getLogger(AnnotationBasedPaymentEventsSubscriber::class.java)

    @SubscribeEvent
    fun taskCreatedSubscriber(event: PaymentAttemptEvent) {
        logger.info("attempt to pay the order: paymentId {}, orderId {}, sum {}, status {}",
            event.paymentId, event.orderId, event.sum, event.status)
    }
    @SubscribeEvent
    fun taskCreatedSubscriber(event: PaymentCompletedSuccessfullyEvent) {
        logger.info("payment completed successfully: paymentId {}, orderId {}, sum {}, status {}",
            event.paymentId, event.orderId, event.sum, event.status)
    }

    @SubscribeEvent
    fun taskCreatedSubscriber(event: PaymentFailedEvent) {
        logger.info("payment failed: paymentId {}, orderId {}, sum {}, status {}",
            event.paymentId, event.orderId, event.sum, event.status)
    }
}