package com.itmo.microservices.demo.payment.model

import java.util.*

data class PaymentSubmissionDto(
    val timestamp: Long,
    val transactionId: UUID
)