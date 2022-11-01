package com.itmo.microservices.demo.delivery.logic

import java.sql.Time

class Delivery : AggregateState<UUID, DeliveryAggregate> {
    private lateinit var deliveryId: UUID
    private lateinit var UseerId: UUID
    var allDelivery: MutableMap<UUID, DeliverySlot> = mutableMapOf()

    fun createNewDelivery(deliveryId: UUID = UUID.randomUUID()): DeliveryCreatedEvent {
        return DeliveryCreatedEvent(deliveryId)
    }
    fun deleteNewDelivery(deliveryId: UUID = UUID.randomUUID()): DeliveryDeletedEvent {
        deliveryId = -1;
        UseerId = -1;
        allDelivery.remove(deliveryId);
    }

}


data class DeliverySlot(
    val id: UUID,
    val timestart:Time,
    val timeFinish:Time
) {

}