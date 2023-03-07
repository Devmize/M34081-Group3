package com.itmo.microservices.demo.delivery.logic

import com.itmo.microservices.demo.delivery.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.util.*
import kotlin.properties.Delegates

class DeliveryAggregateState : AggregateState<UUID, DeliveryAggregate> {
    private lateinit var deliveryId: UUID
    private var timestamp by Delegates.notNull<Long>()
    private lateinit var status: DeliveryStatus
    private lateinit var address: String
    private lateinit var phoneNumber: String
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    override fun getId(): UUID {
        return deliveryId
    }

    fun createDelivery(orderId: UUID, timestamp: Long, address: String, phoneNumber: String): DeliveryAttemptEvent {
        return DeliveryAttemptEvent(
            deliveryId = orderId,
            timestamp = timestamp,
            status = DeliveryStatus.Pending,
            address = address,
            phoneNumber = phoneNumber
        )
    }

    fun updateStatus(orderId: UUID, timestamp: Long, address: String, phoneNumber: String): Event<DeliveryAggregate> {
        return DeliveryAttemptEvent(
            deliveryId = orderId,
            timestamp = timestamp,
            status = DeliveryStatus.Pending,
            address = address,
            phoneNumber = phoneNumber
        )
    }

    @StateTransitionFunc
    fun deliveryAttemptApply(event: DeliveryAttemptEvent) {
        deliveryId = event.deliveryId;
        timestamp = event.timestamp;
        status = event.status;
        address = event.address;
        phoneNumber = event.phoneNumber
    }

    @StateTransitionFunc
    fun deliverySuccessApply(event: DeliveryCompletedSuccessfullyEvent) {
        status = event.status
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun deliveryFailedApply(event: DeliveryFailedEvent) {
        status = event.status
        updatedAt = event.createdAt
    }
}

