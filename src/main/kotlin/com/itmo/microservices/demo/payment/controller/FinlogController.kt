package com.itmo.microservices.demo.payment.controller

import com.itmo.microservices.demo.mongo.FinlogRepo
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
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
                        @Autowired val repo: FinlogRepo) {

    @GetMapping("")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getFinlog(@RequestParam(required = false) order_id: UUID) : List<UserAccountFinancialLogRecordDto> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String
        if (principal is UserDetails) {
            username = principal.username
        } else {
            username = principal.toString()
        }
        
        if (order_id != null) {
            val log = listOf(repo.findByPaymentId(order_id))
            val res = mutableListOf<UserAccountFinancialLogRecordDto>()
            if (log[0]?.userName == username)
                res.add(log[0]!!.toFinancialLog())
        }
        val logs = repo.findAll()
        val res = mutableListOf<UserAccountFinancialLogRecordDto>()
        for (log in logs) {
            if (log.userName == username)
                res.add(log.toFinancialLog())
        }
        return res
    }
}