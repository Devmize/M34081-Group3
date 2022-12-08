package com.itmo.microservices.demo.delivery.api

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.sql.Time
import java.sql.Timestamp
import java.util.*

enum class DeliveryStatus{
    Pending, Success, Failed
}

const val DELIVERY_STARTED = "DELIVERY_STARTED_EVENT"
const val DELIVERY_COMPLETED_SUCCESSFULLY = "DELIVERY_COMPLETED_SUCCESSFULLY_EVENT"
const val DELIVERY_FAILED = "DELIVERY_FAILED_EVENT"

@DomainEvent(name = DELIVERY_STARTED)
class PaymentAttemptEvent(
    val time: Timestamp,
    val phoneNum: String,
    val idnum: UUID
) : Event<DeliveryAggregate>(
    name = DELIVERY_STARTED,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = DELIVERY_COMPLETED_SUCCESSFULLY)
class DeliveryCompletedSuccessfullyEvent(
    val time: Timestamp,
    val idnum: UUID
) : Event<DeliveryAggregate>(
    name = DELIVERY_COMPLETED_SUCCESSFULLY,
    createdAt = System.currentTimeMillis(),
)

@DomainEvent(name = DELIVERY_FAILED)
class PaymentFailedEvent(
    val time: Time,
    val idnum: UUID
) : Event<DeliveryAggregate>(
    name = DELIVERY_FAILED  ,
    createdAt = System.currentTimeMillis(),
)