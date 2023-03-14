package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import com.itmo.microservices.demo.delivery.dto.BookingDto
import com.itmo.microservices.demo.delivery.logic.DeliveryAggregateState
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.dto.AddItemDto
import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.order.logic.Order
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.PaymentSubmissionDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(private val orderEsService: EventSourcingService<UUID, OrderAggregate, Order>,
                      val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>,
                      val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>, ) {

    @PostMapping("/create")
    @Operation(
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun createOrder(@RequestBody request: OrderDto): OrderDto {
        val event = orderEsService.create {
            it.createNewOrder(request.id, request.status, request.itemsMap, request.timeCreated, request.deliveryDuration, request.paymentHistory)
        }

        return OrderDto(event.id, event.createdAt, event.status, event.itemsMap, event.deliveryDuration, event.paymentHistory)
    }

    @PostMapping("/{orderId}/items/{itemId}")
    @Operation(
        summary = "Add item into order",
        responses = [
            ApiResponse(description = "Item added into order", responseCode = "200"),
            ApiResponse(description = "Can't add item into order", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun addItemIntoOrder(@PathVariable orderId: UUID,
                         @PathVariable itemId: UUID,
                         @RequestParam amount: Int
    ) {
        orderEsService.update(orderId) {
            it.addItemIntoOrder(orderId, itemId, amount)
        }
    }

    @PostMapping("/{orderId}")
    @Operation(
        summary = "Add item into order",
        responses = [
            ApiResponse(description = "Item added into order", responseCode = "200"),
            ApiResponse(description = "Can't add item into order", responseCode = "404", content = [Content()])
        ],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun addItemIntoOrder(@RequestBody request: AddItemDto) {
        orderEsService.update(request.orderId) {
            it.addItemIntoOrder(request.orderId, request.itemId, request.amount)
        }
    }

    @GetMapping("/{orderId}")
    @Operation(
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getOrder(@PathVariable orderId: UUID): OrderDto? {
        val order = orderEsService.getState(orderId)
        if (order != null) {
            return OrderDto(order.getId(), order.timeCreated, order.status, order.itemsMap, order.deliveryDuration, order.paymentHistory)
        }

        return null
    }

    @PostMapping("/{order_id}/payment")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun createPayment(@PathVariable order_id: UUID) : PaymentSubmissionDto? {
        val order = orderEsService.getState(order_id) ?: return null
        var sum = 0;
        for (item in order.itemsMap) {
            sum += item.value
        }
        paymentEsService.create { it.tryToPay(order_id, sum) }
        return PaymentSubmissionDto(System.currentTimeMillis(), UUID.randomUUID())
    }

    @PostMapping("/{order_id}/delivery")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun setTimeOfDelivery(@RequestParam slot: Int, @PathVariable order_id: UUID) : BookingDto {
        deliveryEsService.create { it.createDelivery(order_id, slot.toLong(), "", "") }
        return BookingDto(UUID.randomUUID(), setOf(UUID.randomUUID()))
    }
}