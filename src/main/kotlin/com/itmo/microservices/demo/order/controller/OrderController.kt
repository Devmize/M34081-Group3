package com.itmo.microservices.demo.order.controller

import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.logic.Order
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(val orderEsService: EventSourcingService<UUID, OrderAggregate, Order>,
    val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>) {

    @PostMapping("/{order_id}/payment")
    fun createPayment(@PathVariable order_id: UUID) : PaymentAttemptEvent {
        return paymentEsService.create { it.tryToPay(orderId = order_id, sum = 0) }
    }
}