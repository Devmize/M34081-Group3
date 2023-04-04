package com.itmo.microservices.demo.payment.logic

import com.itmo.microservices.demo.payment.api.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
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

    fun createNewPayment(orderId: UUID, sum: Int): PaymentAttemptEvent {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }
        paymentId = UUID.randomUUID()
        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            userName = username,
            sum = sum,
            status = PaymentStatus.Pending
        )
    }

    fun updateStatus(orderId: UUID, sum: Int, status: PaymentStatus): Event<PaymentAggregate> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String = if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }
        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            userName = username,
            sum = sum,
            status = status
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