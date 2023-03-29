package com.itmo.microservices.demo.delivery.projections

import com.itmo.microservices.demo.delivery.model.DeliveryInfoRecord
import com.itmo.microservices.demo.delivery.model.DeliverySubmissionOutcome
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class DeliveryViewDomain {
    @Document("payment-view")
    data class Delivery(
    val userName: String,
    val outcome: DeliverySubmissionOutcome,
    val preparedTime: Long,
    val attempts: Int,
    val submittedTime: Long,
    val transactionId: UUID,
    val submissionStartedTime: Long) {
        fun toDeliveryLog() = DeliveryInfoRecord(
            outcome = outcome,
            preparedTime = preparedTime,
            attempts = attempts,
            submittedTime = submittedTime,
            transactionId = transactionId,
            submissionStartedTime = submissionStartedTime
        )
    }
}
