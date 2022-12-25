package com.itmo.microservices.demo.payment.logic

import com.itmo.microservices.demo.payment.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.util.*
import kotlin.properties.Delegates

class PaymentAggregateState : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    private lateinit var orderId: UUID
    private var sum by Delegates.notNull<Int>()
    private lateinit var status: PaymentStatus
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    override fun getId(): UUID {
        return paymentId
    }

    // чените сами это какая то чушь написана

//    fun tryToPay(orderId: UUID, sum: Amount): PaymentAttemptEvent {
//        httpRequest("http://externalsystem/payment": orderId, sum)
//        return PaymentAttemptEvent(
//            paymentId = paymentId,
//            orderId = orderId,
//            sum = sum,
//            status = PaymentStatus.Pending
//        )
//    }
//
//    fun updateStatus(orderId: UUID, sum: Amount): Event<PaymentAggregate> {
//        httpRequest("http://externalsystem/payment": orderId, sum)
//
//        if (httpResponse.status == "failure")
//            return PaymentFailedEvent(
//                paymentId = paymentId,
//                orderId = orderId,
//                sum = sum,
//                status = PaymentStatus.Failed
//            )
//
//        if (httpResponse.status == "success")
//            return PaymentCompletedSuccessfullyEvent(
//                paymentId = paymentId,
//                orderId = orderId,
//                sum = sum,
//                status = PaymentStatus.Success
//            )
//
//        return PaymentAttemptEvent(
//            paymentId = paymentId,
//            orderId = orderId,
//            sum = sum,
//            status = PaymentStatus.Pending
//        )
//    }
        if (httpResponse.status == "failure")
            return PaymentFailedEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum,
                status = PaymentStatus.Failed
            )

        if (httpResponse.status == "success")
            return PaymentCompletedSuccessfullyEvent(
                paymentId = paymentId,
                orderId = orderId,
                sum = sum,
                status = PaymentStatus.Success
            )
*/
        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            sum = sum,
            status = PaymentStatus.Pending
        )
    }

    @StateTransitionFunc
    fun paymentAttemptApply(event: PaymentAttemptEvent) {
        status = event.status
        sum = event.sum
        orderId = event.orderId
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun paymentSuccessApply(event: PaymentCompletedSuccessfullyEvent) {
        status = event.status
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun paymentFailedApply(event: PaymentFailedEvent) {
        status = event.status
        updatedAt = event.createdAt
    }
}