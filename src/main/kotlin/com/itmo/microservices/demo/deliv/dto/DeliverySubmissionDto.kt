package com.itmo.microservices.demo.deliv.dto

import java.util.*

data class DeliverySubmissionDto(
    val timestamp: Long,
    val transactionId: UUID
)