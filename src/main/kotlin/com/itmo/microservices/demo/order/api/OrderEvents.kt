package com.itmo.microservices.demo.order.api

import com.itmo.microservices.demo.catalog.Product
import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.math.BigDecimal
import java.util.*

const val ORDER_CREATED = "ORDER_CREATED_EVENT"

@DomainEvent(name = ORDER_CREATED)
class OrderCreatedEvent(
    var orderId: UUID,
    var orderItems: Array<Product>,
    var price: BigDecimal,
    var date: Date
): Event<OrderAggregate>(
    name = ORDER_CREATED,
    createdAt = System.currentTimeMillis()
)