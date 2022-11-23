package com.itmo.microservices.demo.order.logic

import com.itmo.microservices.demo.catalog.Product
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import com.itmo.microservices.demo.order.api.model.Amount
import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordModel
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id

class Order: AggregateState<UUID, OrderAggregate> {

    private lateinit var id: UUID
    private lateinit var status: OrderStatus
    private lateinit var itemsMap: Map<UUID, Amount>
    private lateinit var timeCreated: Number
    private lateinit var deliveryDuration: Number
    private lateinit var paymentHistory: List<PaymentLogRecordModel>

    override fun getId(): UUID? {
        return this.id
    }

    fun createNewOrder(id: UUID = UUID.randomUUID(),
                       status: OrderStatus,
                       itemsMap: Map<UUID, Amount>,
                       timeCreated: Number,
                       deliveryDuration: Number,
                       paymentHistory: List<PaymentLogRecordModel>
    ): OrderCreatedEvent {
        return OrderCreatedEvent(id, status, itemsMap, timeCreated, deliveryDuration, paymentHistory)
    }

    @StateTransitionFunc
    fun createNewOrder(event: OrderCreatedEvent) {
        this.id = event.id
        this.itemsMap = event.itemsMap
        this.status = event.status
        this.timeCreated = event.timeCreated
        this.deliveryDuration = event.deliveryDuration
        this.paymentHistory = event.paymentHistory
    }
}