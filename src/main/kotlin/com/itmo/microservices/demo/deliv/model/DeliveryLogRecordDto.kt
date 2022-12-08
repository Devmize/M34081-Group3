package com.itmo.microservices.demo.deliv.model

import com.itmo.microservices.demo.delivery.api.DeliveryStatus
import java.sql.Timestamp
import java.util.*

data class DeliveryLogRecordDto (
    val timestamp: Timestamp,
    val status: DeliveryStatus,
    val phoneNumber: String,
    val id: UUID
)