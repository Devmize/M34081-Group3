package com.itmo.microservices.demo.order.api

import com.itmo.microservices.demo.catalog.Product
import com.itmo.microservices.demo.order.api.model.Amount
import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordModel
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.math.BigDecimal
import java.util.*

const val ORDER_CREATED = "ORDER_CREATED_EVENT"

@DomainEvent(name = ORDER_CREATED)
class OrderCreatedEvent(
    override var id: UUID,
    var status: OrderStatus,
    var itemsMap: Map<UUID, Amount>,
    var timeCreated: Number,
    var deliveryDuration: Number,
    var paymentHistory: List<PaymentLogRecordModel>
): Event<OrderAggregate>(
    name = ORDER_CREATED,
    createdAt = System.currentTimeMillis()
)