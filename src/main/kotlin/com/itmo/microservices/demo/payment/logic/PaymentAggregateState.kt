package com.itmo.microservices.demo.payment.logic

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import com.itmo.microservices.demo.payment.api.PaymentStatus
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.math.BigDecimal
import java.util.*

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
    private lateinit var orderId: UUID
    private lateinit var sum: BigDecimal
    private lateinit var status: PaymentStatus
    override fun getId(): UUID {
        return paymentId
    }

    fun TryToPay(orderId: UUID, sum: BigDecimal): Event<PaymentAggregate> {
        httpRequest("http://externalsystem/payment": orderId, sum)

        if (httpResponse.status == "failure")
            return PaymentAttemptEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum,
                status = PaymentStatus.Failure
            )

        if (httpResponse.status == "success")
            return PaymentAttemptEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum,
                status = PaymentStatus.Success
            )

        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            sum = sum,
            status = PaymentStatus.Pending
        )
    }

    fun UpdateStatus(orderId: UUID, sum: BigDecimal): PaymentAttemptEvent {
        httpRequest("http://externalsystem/payment": orderId, sum)

        if (httpResponse.status == "failure")
            return PaymentAttemptEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum,
                status = PaymentStatus.Failure
            )

        if (httpResponse.status == "success")
            return PaymentAttemptEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum,
                status = PaymentStatus.Success
            )

        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            sum = sum,
            status = PaymentStatus.Pending
        )
    }

    @StateTransitionFunc
    fun updateInfoAboutPaymentStatus(event: PaymentAttemptEvent) {
        status = event.status
        updatedAt = createdAt
    }
}