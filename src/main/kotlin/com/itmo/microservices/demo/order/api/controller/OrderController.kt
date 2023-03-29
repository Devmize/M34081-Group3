package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.logic.CatalogAggregateState
import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import com.itmo.microservices.demo.delivery.dto.BookingDto
import com.itmo.microservices.demo.delivery.logic.DeliveryAggregateState
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.api.dto.AddItemDto
import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.order.api.dto.ResponseAnswer
import com.itmo.microservices.demo.order.logic.Order
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.PaymentSubmissionDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.logic.UserAggregateState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(private val orderEsService: EventSourcingService<UUID, OrderAggregate, Order>,
                      private val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>,
                      private val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>,
                      private val catalogEsService: EventSourcingService<String, CatalogAggregate, CatalogAggregateState>,
                      private val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {

    @PostMapping("/create")
    @Operation(
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun createOrder(): OrderDto {
        val event = orderEsService.create {
            it.createNewOrder()
        }

        return OrderDto(event.orderId, event.status, event.itemsMap, event.timeCreated, event.deliveryDuration, event.paymentHistory)
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
                         @PathVariable catalogId: String,
                         @PathVariable itemId: UUID,
                         @RequestParam amount: Int
    ): ResponseAnswer {
        val catalog = catalogEsService.getState(catalogId)
        if (catalog != null) {
            val product = catalog.products[itemId]
            if (product != null) {
                if (product.count!! >= amount) {
                    orderEsService.update(orderId) {
                        it.addItemIntoOrder(orderId, itemId, amount)
                    }
                } else {
                    return ResponseAnswer("Catalog doesn't have " + "product with" + amount + "count")
                }
            } else {
                return ResponseAnswer("Catalog doesn't have product")
            }
        } else {
            return ResponseAnswer("Catalog doesn't exist")
        }

        return ResponseAnswer("All fine, be happy, bro!")
    }

    @GetMapping("/{orderId}")
    @Operation(
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getOrder(@PathVariable orderId: UUID): OrderDto? {
        val order = orderEsService.getState(orderId)
        if (order != null) {
            return OrderDto(order.getId(), order.status, order.itemsMap, order.timeCreated, order.deliveryDuration, order.paymentHistory)
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
    fun setTimeOfDelivery(@RequestParam slot_in_sec: Int, @PathVariable order_id: UUID) : BookingDto {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String
        if (principal is UserDetails) {
            username = principal.username
        } else {
            username = principal.toString()
        }
        val user = userEsService.getState(UUID.randomUUID())
        deliveryEsService.create { it.createDelivery(order_id, slot_in_sec.toLong(), "user.address",
            "user.phone") }
        return BookingDto(UUID.randomUUID(), setOf())
    }
}