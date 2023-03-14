package com.itmo.microservices.demo.payment.controller

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.FinancialOperationType
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import com.itmo.microservices.demo.payment.projections.PaymentViewDomain
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/finlog")
class FinlogController (val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>) {

    @GetMapping("")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getFinlog(@RequestParam(required = false) order_id: UUID) : List<UserAccountFinancialLogRecordDto> {
        return listOf(UserAccountFinancialLogRecordDto(FinancialOperationType.WITHDRAW, 100, UUID.randomUUID(),
        UUID.randomUUID(), 123452))
    }

    @PostMapping("/create")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun createFinlog(@RequestBody body: PaymentViewDomain.Payment) : PaymentViewDomain.Payment {
        val log = PaymentViewDomain.Payment(body.paymentId, body.amount, body.orderId, body.paymentTransactionId,
        body.timestamp, body.status)
        return log
    }

}