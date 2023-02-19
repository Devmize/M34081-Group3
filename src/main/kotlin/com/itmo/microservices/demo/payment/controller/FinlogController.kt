package com.itmo.microservices.demo.payment.controller

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/finlog")
class FinlogController (val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>) {
/*
    @GetMapping(value = {"/{order_id}", "" })
    fun getFinlog(@PathVariable(required = false) order_id: UUID) : List<UserAccountFinancialLogRecordDto> {
        if (order_id != null)
            return paymentEsService.getState(order_id)
        else
            return paymentEsService.getState()
    }*/
}