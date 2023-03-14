package com.itmo.microservices.demo.order.api.model

import java.util.*

data class PaymentLogRecordModel(
    val timestamp: Long?,
    val status: PaymentStatus?,
    val amount: Double?,
    val transactionId: UUID?,
)