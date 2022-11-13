package com.itmo.microservices.demo.payment.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.math.BigDecimal
import java.util.*

const val PAYMENT_ATTEMPT = "PAYMENT_ATTEMPT_EVENT"

enum class PaymentStatus{
    Pending, Success, Failure
}

@DomainEvent(name = PAYMENT_ATTEMPT)
class PaymentAttemptEvent(
    val paymentId: UUID,
    val orderId: UUID?,
    val sum: BigDecimal?,
    val status: PaymentStatus,
) : Event<PaymentAggregate>(
    name = PAYMENT_ATTEMPT,
    createdAt = System.currentTimeMillis(),
)