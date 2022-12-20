
package com.itmo.microservices.demo.deliv.dto

import com.itmo.microservices.demo.deliv.api.DeliveryStatus
import java.util.*

data class DeliveryLogRecordDto (
    val timestamp: Long,
    val status: DeliveryStatus,
    val amount: Int,
    val transactionId: UUID
)
