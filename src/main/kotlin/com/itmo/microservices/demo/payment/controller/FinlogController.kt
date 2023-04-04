package com.itmo.microservices.demo.payment.controller

import com.itmo.microservices.demo.mongo.FinlogRepo
import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.logic.Order
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.FinancialOperationType
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.*


@RestController
@RequestMapping("/finlog")
class FinlogController (val paymentEsService: EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState>,
                        private val orderEsService: EventSourcingService<UUID, OrderAggregate, Order>) {

    @GetMapping("")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getFinlog(@RequestParam(required = false) order_id: UUID): List<UserAccountFinancialLogRecordDto> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String
        if (principal is UserDetails) {
            username = principal.username
        } else {
            username = principal.toString()
        }

        val logs = orderEsService.getState(order_id)!!.paymentHistory
        val res = mutableListOf<UserAccountFinancialLogRecordDto>()
        for (log in logs) {
            res.add(
                UserAccountFinancialLogRecordDto(
                    FinancialOperationType.WITHDRAW, log.amount!!.toInt(), order_id,
                    log.transactionId!!, log.timestamp!!
                )
            )
        }
        return res
    }
}