package com.itmo.microservices.demo.delivery.api

import org.springframework.data.domain.DomainEvents
import java.util.UUID


const val DELIVERY_CREATED = "DELIVERY_CREATED_EVENT"
const val DELIVERY_FINISHED = "DELIVERY_FINISHED_EVENT"


@DomainEvents(name = DELIVERY_CREATED)
class DeliveryCreatedEvent(
    val  deliveryId:UUID,
    val userId:UUID,):
        Event<DeliveryAggregate>(
            name = DELIVERY_CREATED,
            createdAt = System.currentTimeMillis(),
)

@DomainEvents(name = DELIVERY_FINISHED)
class DeliveryFinishedEvent(
    val  deliveryId:UUID,
    val userId:UUID,):
    Event<DeliveryAggregate>(
        name = DELIVERY_FINISHED,
        createdAt = System.currentTimeMillis(),
    )

class DeliveryEvents {
    fu
}