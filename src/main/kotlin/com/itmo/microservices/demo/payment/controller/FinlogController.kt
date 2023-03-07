package com.itmo.microservices.demo.payment.controller

import com.itmo.microservices.demo.mongo.FinlogRepo
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/finlog")
class FinlogController (val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>,
                        @Autowired val repo: FinlogRepo
) {

    @GetMapping("")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getFinlog(@RequestParam(required = false) order_id: UUID) : List<UserAccountFinancialLogRecordDto> {
        if (order_id != null) {
            return listOf(repo.findByPaymentId(order_id)!!.toFinancialLog())
        }
        else {
            val logs = repo.findAll()
            val res = mutableListOf<UserAccountFinancialLogRecordDto>()
            for (log in logs) {
                res.add(log.toFinancialLog())
            }
            return res
        }

    }
}