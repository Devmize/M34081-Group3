package com.itmo.microservices.demo.order.logic

import com.itmo.microservices.demo.catalog.Product
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id

class Order: AggregateState<UUID, OrderAggregate> {

    private lateinit var orderId: UUID
    private lateinit var orderItems: Array<Product>
    private lateinit var price: BigDecimal
    private lateinit var date: Date

    override fun getId(): UUID? {
        return this.orderId
    }

    fun createNewOrder(id: UUID = UUID.randomUUID(),
                       orderItems: Array<Product>,
                       price: BigDecimal,
                       date: Date = Date()
    ): OrderCreatedEvent {
        return OrderCreatedEvent(id, orderItems, price, date)
    }

    @StateTransitionFunc
    fun createNewOrder(event: OrderCreatedEvent) {
        this.orderId = event.orderId
        this.orderItems = event.orderItems
        this.price = event.price
        this.date = event.date
    }
}