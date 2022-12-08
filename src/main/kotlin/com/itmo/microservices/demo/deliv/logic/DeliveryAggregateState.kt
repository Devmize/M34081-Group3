package com.itmo.microservices.demo.deliv.logic

import com.itmo.microservices.demo.deliv.api.*
import com.itmo.microservices.demo.delivery.entity.Delivery
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.util.*
import  java.security.*

class DeliveryAggregateState: AggregateState<UUID, DeliveryAggregate> {
    private lateinit var DeliveryId: UUID
    private var timeSlot: Timestamp = Timestamp(0,0,0,0,0);
    private var address: String? = ""
    private var phoneNumber: String? = ""
    private lateinit var status: DeliveryStatus

    override fun getId(): UUID {
        return this.DeliveryId
    }

    fun tryToDeliver(orderId: UUID, time: Timestamp): DeliveryAttemptEvent {
        httpRequest("http://externalsystem/delivery": orderId, time)
        return DeliveryAttemptEvent(
            DeliveryId = orderId,
            timeSlot = time
        )
    }

    fun updateStatus(orderId: UUID, time: Timestamp): Event<DeliveryAggregate> {
        httpRequest("http://externalsystem/delivery": orderId, time)

        if (httpResponse.status == "failure")
            return DeliveryFailedEvent(
                DeliveryId = DeliveryId,
                address = address,
                phoneNumber = phoneNumber,
                status = DeliveryStatus.Failed
            )

        if (httpResponse.status == "success")
            return DeliveryCompletedSuccessfullyEvent(
                DeliveryId = DeliveryId,
                address = address,
                phoneNumber = phoneNumber,
                status = DeliveryStatus.Success
            )

        return DeliveryAttemptEvent(
            DeliveryId = DeliveryId,
            address = address,
            phoneNumber = phoneNumber,
            status = DeliveryStatus.Pending
        )
    }

    @StateTransitionFunc
    fun deliveryAttemptApply(event: deliveryAttemptEvent) {
        DeliveryId = event.deliveryId
        address = event.address
        phoneNumber = event.phoneNumber
    }

    @StateTransitionFunc
    fun DeliverySuccessApply(event: DeliveryCompletedSuccessfullyEvent) {
        address = event.addres
        phoneNumber = event.phoneNumber
    }

    @StateTransitionFunc
    fun paymentFailedApply(event: DeliveyFailedEvent) {
        address = event.addres
        phoneNumber = event.phoneNumber
    }
}