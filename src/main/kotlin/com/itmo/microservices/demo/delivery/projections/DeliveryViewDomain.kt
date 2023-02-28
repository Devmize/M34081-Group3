package com.itmo.microservices.demo.delivery.projections

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class DeliveryViewDomain {
    @Document("payment-view")
    data class Delivery(
    val outcome: Long,
    val preparedTime: Long,
    val attempts: Int,
    val submittedTime: Long,
    val transactionId: UUID,
    val submissionStartedTime: Long
)
}
