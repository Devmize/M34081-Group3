package com.itmo.microservices.demo.payment.logic

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.math.BigDecimal
import java.util.*

class Payment : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    private lateinit var orderId: UUID
    private lateinit var sum: BigDecimal

    override fun getId(): UUID {
        return paymentId
    }

    fun tryToPay(orderId: UUID, sum: BigDecimal): Event<PaymentAggregate> {
        /*
        httpRequest("http://externalsystem/payment": orderId, sum)

        if (httpResponse.status == "failure")
            return PaymentFailedEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum
            )
        if (httpResponse.status == "success")
            return PaymentCompletedSuccessfullyEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum
            )
        */
        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            sum = sum
        )
    }
}