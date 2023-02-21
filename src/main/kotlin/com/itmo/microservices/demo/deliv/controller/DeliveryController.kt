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

    @GetMapping("/deliveryId", "")
    fun attemptDelivery(@PathVariable(required = false) deliveryID: UUID) : DeliveryAttemptEvent {
        if(deliveryID != null) {
            return deliveryEsService.getState {deliveryID };
        } else {
            return deliveryEsService.getState {};
        }
    }

    @PostMapping(value = "/{UUID}/delivery")
    fun CompletedSuccessfullyDelivery(@PathVariable deliveryID: UUID) : DeliveryCompletedSuccessfullyEvent {
        return deliveryEsService.create(CompleteDeliv(deliveryID = deliveryID)  );
    }

    @PostMapping(value = "/{UUID}/delivery")
    fun FailedDelivery(@PathVariable deliveryID: UUID) : DeliveryFailedEvent {
        return deliveryEsService.create(FailedDeliv(deliveryID = deliveryID));
    }
}