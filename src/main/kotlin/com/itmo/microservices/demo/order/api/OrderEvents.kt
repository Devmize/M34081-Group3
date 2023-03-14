package com.itmo.microservices.demo.order.api

import com.itmo.microservices.demo.order.api.dto.PaymentLogRecord
import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordModel
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val ORDER_CREATED = "ORDER_CREATED_EVENT"
const val ORDER_ADD_ITEM = "ORDER_ADD_ITEM_EVENT"

@DomainEvent(name = ORDER_CREATED)
data class OrderCreatedEvent(
    val orderId: UUID,
    val status: OrderStatus,
    val itemsMap: Map<UUID, Double>,
    val timeCreated: Number,
    val deliveryDuration: Number,
    val paymentHistory: List<PaymentLogRecord>
): Event<OrderAggregate>(
    name = ORDER_CREATED
)

@DomainEvent(name = ORDER_ADD_ITEM)
class OrderAddItemEvent(
    val orderId: UUID,
    val itemId: UUID,
    val amount: Number
): Event<OrderAggregate>(
    name = ORDER_ADD_ITEM
)