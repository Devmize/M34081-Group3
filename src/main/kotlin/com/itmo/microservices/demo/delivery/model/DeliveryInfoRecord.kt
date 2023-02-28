package com.itmo.microservices.demo.delivery.model

import java.util.*

enum class DeliverySubmissionOutcome{
    SUCCESS, FAILURE, EXPIRED
}

data class DeliveryInfoRecord (
    val outcome: DeliverySubmissionOutcome,
    val preparedTime: Long,
    val attempts: Int,
    val submittedTime: Long,
    val transactionId: UUID,
    val submissionStartedTime: Long
)