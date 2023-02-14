package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.order.api.OrderCreatedEvent
import com.itmo.microservices.demo.order.logic.Order
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/orders")
class OrderController() {

    @GetMapping("/")
    fun createOrder() : OrderCreatedEvent? {
        return null
    }

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: UUID) : Order? {
        return null
    }

    @GetMapping("/")
    fun getOrders() : Array<Order>? {
        return null
    }


}