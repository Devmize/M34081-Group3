package com.itmo.microservices.demo.payment.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PAYMENT_ATTEMPT = "PAYMENT_ATTEMPT_EVENT"
const val PAYMENT_COMPLETED_SUCCESSFULLY = "PAYMENT_COMPLETED_SUCCESSFULLY_EVENT"
const val PAYMENT_FAILED = "PAYMENT_FAILED_EVENT"

enum class PaymentStatus{
    Pending, Success, Failed
}

@DomainEvent(name = PAYMENT_ATTEMPT)
class PaymentAttemptEvent(
    val paymentId: UUID,
    val orderId: UUID,
    val sum: Int,
    val status: PaymentStatus,
) : Event<PaymentAggregate>(
    name = PAYMENT_ATTEMPT,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = PAYMENT_COMPLETED_SUCCESSFULLY)
class PaymentCompletedSuccessfullyEvent(
    val paymentId: UUID,
    val orderId: UUID,
    val sum: Int,
    val status: PaymentStatus,
) : Event<PaymentAggregate>(
    name = PAYMENT_COMPLETED_SUCCESSFULLY,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = PAYMENT_FAILED)
class PaymentFailedEvent(
    val paymentId: UUID,
    val orderId: UUID,
    val sum: Int,
    val status: PaymentStatus,
) : Event<PaymentAggregate>(
    name = PAYMENT_FAILED,
    createdAt = System.currentTimeMillis(),
)