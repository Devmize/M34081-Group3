package com.itmo.microservices.demo._internal.controller

import com.itmo.microservices.demo.delivery.model.BookingLogRecord
import com.itmo.microservices.demo.delivery.model.DeliveryInfoRecord
import com.itmo.microservices.demo.mongo.DeliveryLogRepo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/_internal")
class InternalController(@Autowired val deliveryLogRepo: DeliveryLogRepo) {

    @GetMapping("/deliveryLog/{orderId}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getDeliveryInfoRecord(@PathVariable orderId: UUID): List<DeliveryInfoRecord> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String
        if (principal is UserDetails) {
            username = principal.username
        } else {
            username = principal.toString()
        }
        val logs = deliveryLogRepo.findAll()
        val res = mutableListOf<DeliveryInfoRecord>()
        for (log in logs) {
            if (log.userName == username)
                if (orderId == log.transactionId)
                    res.add(log.toDeliveryLog())
        }
        return res
    }

    @GetMapping("/bookingHistory/{bookingId}")
    @Operation(security = [SecurityRequirement(name = "bearerAuth")])
    fun getBookingLog(@PathVariable bookingId: UUID): List<BookingLogRecord> {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val username: String
        if (principal is UserDetails) {
            username = principal.username
        } else {
            username = principal.toString()
        }
        return mutableListOf()
    }
}