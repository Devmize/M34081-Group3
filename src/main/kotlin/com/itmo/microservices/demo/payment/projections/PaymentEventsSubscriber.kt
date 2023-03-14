package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import com.itmo.microservices.demo.payment.api.PaymentCompletedSuccessfullyEvent
import com.itmo.microservices.demo.payment.api.PaymentFailedEvent
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.core.EventSourcingService
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class PaymentEventsSubscriber(
    private val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>
) {

    val logger: Logger = LoggerFactory.getLogger(PaymentEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(PaymentAggregate::class, "payment") {

            `when`(PaymentAttemptEvent::class) { event ->
                paymentEsService.update(event.paymentId) {
                    it.tryToPay(event.orderId, event.sum)
                }
                logger.info("attempt to pay the order: paymentId {}, orderId {}, sum {}, status {}",
                    event.paymentId, event.orderId, event.sum, event.status)
            }
            `when`(PaymentCompletedSuccessfullyEvent::class) { event ->
                paymentEsService.update(event.paymentId) {
                    it.updateStatus(event.orderId, event.sum, event.status)
                }
                logger.info("payment completed successfully: paymentId {}, orderId {}, sum {}, status {}",
                    event.paymentId, event.orderId, event.sum, event.status)
            }
            `when`(PaymentFailedEvent::class) { event ->
                paymentEsService.update(event.paymentId) {
                    it.updateStatus(event.orderId, event.sum, event.status)
                }
                logger.info("payment failed: paymentId {}, orderId {}, sum {}, status {}",
                    event.paymentId, event.orderId, event.sum, event.status)
            }
        }
    }
}