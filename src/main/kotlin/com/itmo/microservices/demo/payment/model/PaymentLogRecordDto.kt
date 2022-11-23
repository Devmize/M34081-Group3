package com.itmo.microservices.demo.payment.model

import com.itmo.microservices.demo.payment.api.PaymentStatus
import java.util.*

data class PaymentLogRecordDto (
    val timestamp: Long,
    val status: PaymentStatus,
    val amount: Amount,
    val transactionId: UUID
)