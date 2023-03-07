package com.itmo.microservices.demo.delivery.controller

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import com.itmo.microservices.demo.delivery.logic.DeliveryAggregateState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/delivery")
class DeliveryController(val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, DeliveryAggregateState>) {

    @GetMapping("/slots")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun createPayment(@RequestParam number: Int) : List<Int> {
        deliveryEsService.create { it.createDelivery(it.getId(), number.toLong(), "", "") }
        return listOf(1, 2)
    }
}