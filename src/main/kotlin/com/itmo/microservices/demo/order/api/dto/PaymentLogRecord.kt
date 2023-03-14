package com.itmo.microservices.demo.order.api.dto

import com.itmo.microservices.demo.payment.api.PaymentStatus
import java.util.*

data class PaymentLogRecord(
    val timestamp: Long?,
    val status: PaymentStatus?,
    val amount: Double?,
    val transactionId: UUID?,
)