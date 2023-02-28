package com.itmo.microservices.demo.payment.controller

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/finlog")
class FinlogController (val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>) {

    @GetMapping("")
    fun getFinlog(@RequestParam(required = false) order_id: UUID) : List<UserAccountFinancialLogRecordDto> {
        val ret = listOf<UserAccountFinancialLogRecordDto>()
        return if (order_id != null)
            listOf<UserAccountFinancialLogRecordDto>(ret.last())
        else
            ret
    }
}