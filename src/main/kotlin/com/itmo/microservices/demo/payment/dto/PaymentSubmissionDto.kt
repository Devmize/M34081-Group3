package com.itmo.microservices.demo.payment.dto

import java.util.*

data class PaymentSubmissionDto(
    val timestamp: Long,
    val transactionId: UUID
)