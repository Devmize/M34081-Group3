package com.itmo.microservices.demo.order.controller

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import com.itmo.microservices.demo.delivery.dto.BookingDto
import com.itmo.microservices.demo.delivery.logic.DeliveryAggregateState
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.logic.Order
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.PaymentSubmissionDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(val orderEsService: EventSourcingService<UUID, OrderAggregate, Order>,
                      val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>,
                      val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>,
) {

    @PostMapping("/{order_id}/payment")
    fun createPayment(@PathVariable order_id: UUID) : PaymentSubmissionDto {
        paymentEsService.create { it.tryToPay(order_id, 0) }
        return PaymentSubmissionDto(System.currentTimeMillis(), UUID.randomUUID())
    }

    @PostMapping("/{order_id}/delivery")
    fun setTimeOfDelivery(@RequestParam slot: Int, @PathVariable order_id: UUID) : BookingDto {
        deliveryEsService.create { it.createDelivery(order_id, slot.toLong(), "", "") }
        return BookingDto(UUID.randomUUID(), setOf(UUID.randomUUID()))
    }
}