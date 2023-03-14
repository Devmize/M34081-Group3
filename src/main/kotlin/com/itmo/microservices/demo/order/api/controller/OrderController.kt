package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.dto.AddItemDto
import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.order.logic.Order
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(private val orderEsService: EventSourcingService<UUID, OrderAggregate, Order>) {

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

//    @PostMapping("/{orderId}/items/{itemId}?amount={amount}")
//    @Operation(
//        summary = "Add item into order",
//        responses = [
//            ApiResponse(description = "Item added into order", responseCode = "200"),
//            ApiResponse(description = "Can't add item into order", responseCode = "404", content = [Content()])
//        ],
//        security = [SecurityRequirement(name = "bearerAuth")]
//    )
//    fun addItemIntoOrder(@PathVariable orderId: UUID,
//                         @PathVariable itemId: UUID,
//                         @PathVariable amount: Int
//    ) {
//        orderEsService.update(orderId) {
//            it.addItemIntoOrder(orderId, itemId, amount)
//        }
//    }

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

}