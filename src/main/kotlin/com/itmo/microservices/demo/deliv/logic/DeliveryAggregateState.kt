
package com.itmo.microservices.deliv.payment.logic

import com.itmo.microservices.demo.deliv.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.sql.Timestamp
import java.util.*
import kotlin.properties.Delegates

class DeliveryAggregateState : AggregateState<UUID, DeliveryAggregate> {
    private lateinit var deliveryId: UUID
    private lateinit var timestamp: Timestamp
    private lateinit var status: DeliveryStatus
    private lateinit var address: String
    private lateinit var phoneNumber: String
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    override fun getId(): UUID {
        return deliveryId
    }

    fun tryToPay(orderId: UUID, sum: Int, timestamp: Timestamp): DeliveryAttemptEvent {
        /*
        httpRequest("http://externalsystem/delivery": orderId, sum)
        */
        return DeliveryAttemptEvent(
            deliveryId = orderId,
            timestamp = timestamp,
            status = DeliveryStatus.Pending,
            address = "",
            phoneNumber = ""
        )
    }

    fun updateStatus(orderId: UUID, sum: Int, timestamp: Timestamp): Event<DeliveryAggregate> {
        /*
        httpRequest("http://externalsystem/delivery": orderId, sum)

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
        return DeliveryAttemptEvent(
            deliveryId = orderId,
            timestamp = timestamp,
            status = DeliveryStatus.Pending,
            address = "",
            phoneNumber = ""
        )
    }

    @StateTransitionFunc
    fun DeliveryAttemptApply(event: DeliveryAttemptEvent) {

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

