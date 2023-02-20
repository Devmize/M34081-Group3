package com.itmo.microservices.demo.deliv.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import com.itmo.microservices.demo.deliv.api.DeliveryAggregate
import com.itmo.microservices.demo.deliv.logic.DeliveryAggregateState
import ru.quipy.core.EventSourcingService
import java.util.*
@RestController
@RequestMapping("/Delivery")
class DeliveryController(
    val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, Account>
) {

    @PostMapping("/{UUID/delivery}")
    fun attemptDelivery(@PathVariable holderId: UUID) : DeliveryAttemptEvent {
        return deliveryEsService.create { it.createNewDelivery(holderId = holderId) }
    }

    @PostMapping("/{UUID/delivery}")
    fun CompletedSuccessfullyDelivery(@PathVariable holderId: UUID) : DeliveryCompletedSuccessfullyEvent {
        return deliveryEsService.getState(holderId , "Success");
    }

    @PostMapping("/{UUID/delivery}")
    fun FailedDelivery(@PathVariable holderId: UUID) : DeliveryFailedEvent {
        return deliveryEsService.getState(holderId, "Success");
    }
}