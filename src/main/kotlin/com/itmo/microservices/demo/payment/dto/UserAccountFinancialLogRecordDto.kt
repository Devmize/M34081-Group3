package com.itmo.microservices.demo.payment.dto


import java.util.*

enum class FinancialOperationType {
    WITHDRAW, REFUND
}

data class UserAccountFinancialLogRecordDto(
    val type: FinancialOperationType,
    val amount: Int,
    val orderId: UUID,
    val paymentTransactionId: UUID,
    val timestamp: Long
)