
package com.itmo.microservices.demo.deliv.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.sql.Timestamp
import java.util.*

const val DELIVERY_ATTEMPT = "DELIVERY_ATTEMPT_EVENT"
const val DELIVERY_COMPLETED_SUCCESSFULLY = "DELIVERY_COMPLETED_SUCCESSFULLY_EVENT"
const val DELIVERY_FAILED = "DELIVERY_FAILED_EVENT"

enum class DeliveryStatus{
    Pending, Success, Failed
}

@DomainEvent(name = DELIVERY_ATTEMPT)
class DeliveryAttemptEvent(
    val deliveryId: UUID,
    val timestamp: Timestamp,
    val status: DeliveryStatus,
    val address: String,
    val phoneNumber: String,
) : Event<DeliveryAggregate>(
    name = DELIVERY_ATTEMPT,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = DELIVERY_COMPLETED_SUCCESSFULLY)
class DeliveryCompletedSuccessfullyEvent(
    val deliveryId: UUID,
    val timestamp: Timestamp,
    val status: DeliveryStatus,
    val address: String,
    val phoneNumber: String,
) : Event<DeliveryAggregate>(
    name = DELIVERY_COMPLETED_SUCCESSFULLY,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = DELIVERY_FAILED)
class DeliveryFailedEvent(
    val deliveryId: UUID,
    val timestamp: Timestamp,
    val status: DeliveryStatus,
    val address: String,
    val phoneNumber: String,
) : Event<DeliveryAggregate>(
    name = DELIVERY_FAILED,
    createdAt = System.currentTimeMillis(),
)