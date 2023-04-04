package com.itmo.microservices.demo.order.api

import com.itmo.microservices.demo.order.api.dto.PaymentLogRecord
import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.payment.api.PaymentStatus
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val ORDER_CREATED = "ORDER_CREATED_EVENT"
const val ORDER_ADD_ITEM = "ORDER_ADD_ITEM_EVENT"
const val ORDER_ADD_PAYMENT = "ORDER_ADD_PAYMENT_EVENT"

@DomainEvent(name = ORDER_CREATED)
data class OrderCreatedEvent(
    val orderId: UUID,
    val status: OrderStatus,
    val itemsMap: Map<UUID, Int>,
    val timeCreated: Number,
    val deliveryDuration: Number,
    val paymentHistory: MutableList<PaymentLogRecord>
): Event<OrderAggregate>(
    name = ORDER_CREATED
)

@DomainEvent(name = ORDER_ADD_ITEM)
class OrderAddItemEvent(
    val orderId: UUID,
    val itemId: UUID,
    val amount: Int
): Event<OrderAggregate>(
    name = ORDER_ADD_ITEM
)

@DomainEvent(name = ORDER_ADD_PAYMENT)
class OrderAddPaymentEvent(
    val orderId: UUID,
    val status: PaymentStatus,
    val sum: Int,
    val transactionId: UUID,
): Event<OrderAggregate>(
    name = ORDER_ADD_PAYMENT
)