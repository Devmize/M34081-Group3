package com.itmo.microservices.demo.order.api

import com.itmo.microservices.demo.order.api.dto.PaymentLogRecord
import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordModel
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val ORDER_CREATED = "ORDER_CREATED_EVENT"
const val ORDER_ADD_ITEM = "ORDER_ADD_ITEM_EVENT"
const val ORDER_BOOKED = "ORDER_BOOKED"
const val ORDER_UPDATE = "ORDER_UPDATE"

@DomainEvent(name = ORDER_CREATED)
data class OrderCreatedEvent(
    val orderId: UUID,
    val status: OrderStatus,
    val itemsMap: Map<UUID, Int>,
    val timeCreated: Number,
    val deliveryDuration: Number,
    val paymentHistory: List<PaymentLogRecord>
): Event<OrderAggregate>(
    name = ORDER_CREATED
)

@DomainEvent(name = ORDER_ADD_ITEM)
data class OrderAddItemEvent(
    val orderId: UUID,
    val itemId: UUID,
    val amount: Int
): Event<OrderAggregate>(
    name = ORDER_ADD_ITEM
)

@DomainEvent(name = ORDER_UPDATE)
data class OrderUpdateItemsEvent(
    val items: MutableMap<UUID, Int>
): Event<OrderAggregate>(
    name = ORDER_UPDATE
)

@DomainEvent(name = ORDER_BOOKED)
data class OrderBookedEvent(
    val orderId: UUID
): Event<OrderAggregate>(
    name = ORDER_BOOKED
)