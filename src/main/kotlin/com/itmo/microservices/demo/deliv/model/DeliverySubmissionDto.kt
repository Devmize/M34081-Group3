package com.itmo.microservices.demo.deliv.model


import java.sql.Timestamp
import java.util.*

data class DeliverySubmissionDto(
    val timestamp: Timestamp,
    val DeliveryId: UUID
)