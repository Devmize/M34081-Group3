package com.itmo.microservices.demo.user.model

import java.util.*

data class UserAccountFinancialLogRecordDto(
    val type: FinancialOperationType,
    val amount: Int,
    val orderId: UUID,
    val paymentTransactionId: UUID,
    val timestamp: Long
)
