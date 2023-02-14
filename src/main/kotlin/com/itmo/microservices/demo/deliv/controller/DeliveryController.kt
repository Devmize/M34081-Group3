package com.itmo.microservices.demo.deliv.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate
import java.util.*

@RestController
@RequestMapping("/Delivery")
class DeliveryController(
    val deliveryEsService: EventSourcingService<UUID, DeliveryAggregate, Account>
) {

    @PostMapping("/{holderId}")
    fun createAccount(@PathVariable holderId: UUID) : DeluveryCreatedEvent {
        return accountEsService.create { it.createNewDelivery(holderId = holderId) }
    }

    @GetMapping("/{deluveryId}")
    fun getDelivery(@PathVariable accountId: UUID) : Deluvery? {
        return DeluveryEsService.getState(deliveryId)
    }
}