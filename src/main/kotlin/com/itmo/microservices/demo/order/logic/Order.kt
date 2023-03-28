package com.itmo.microservices.demo.order.logic

import com.itmo.microservices.demo.catalog.logic.Order
import com.itmo.microservices.demo.order.api.OrderAddItemEvent
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import com.itmo.microservices.demo.order.api.dto.PaymentLogRecord
import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordModel
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class Order: AggregateState<UUID, OrderAggregate> {

    private lateinit var id: UUID
    lateinit var status: OrderStatus
    lateinit var itemsMap: MutableMap<UUID, Int>
    lateinit var timeCreated: Number
    lateinit var deliveryDuration: Number
    lateinit var paymentHistory: List<PaymentLogRecord>

    override fun getId(): UUID {
        return this.id
    }

    fun createNewOrder(id: UUID = UUID.randomUUID(),
                       status: OrderStatus = OrderStatus.COLLECTING,
                       itemsMap: MutableMap<UUID, Int> = mutableMapOf<UUID, Int>(),
                       timeCreated: Number = System.currentTimeMillis(),
                       deliveryDuration: Number = 0,
                       paymentHistory: List<PaymentLogRecord> = listOf<PaymentLogRecord>()
    ): OrderCreatedEvent {
        return OrderCreatedEvent(id, status, itemsMap, timeCreated, deliveryDuration, paymentHistory)
    }

    fun addItemIntoOrder(orderId: UUID,
                         itemId: UUID,
                         amount: Int
    ): OrderAddItemEvent {
        return OrderAddItemEvent(orderId, itemId, amount)
    }

    @StateTransitionFunc
    fun createNewOrder(event: OrderCreatedEvent) {
        this.id = event.id
        this.status = event.status
        this.itemsMap = event.itemsMap as MutableMap<UUID, Int>
        this.timeCreated = event.timeCreated
        this.deliveryDuration = event.deliveryDuration
        this.paymentHistory = event.paymentHistory
    }

    // Will add check for item
    @StateTransitionFunc
    fun addItemIntoOrder(event: OrderAddItemEvent) {
        if (this.itemsMap[event.itemId] != null) {
            this.itemsMap[event.itemId] = this.itemsMap[event.itemId]!! + event.amount as Int
        } else {
            this.itemsMap[event.itemId] = event.amount as Int
        }
    }
}