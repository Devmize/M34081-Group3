package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.payment.api.PaymentStatus
import com.itmo.microservices.demo.payment.dto.FinancialOperationType
import com.itmo.microservices.demo.payment.dto.UserAccountFinancialLogRecordDto
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class PaymentViewDomain {
    @Document("payment-view")
    data class Payment(
        val paymentId: UUID,
        val amount: Int,
        val orderId: UUID,
        val paymentTransactionId: UUID,
        val timestamp: Long,
        val status: PaymentStatus,
    ) {
        fun toFinancialLog() = UserAccountFinancialLogRecordDto(
            type = FinancialOperationType.REFUND,
            amount = amount,
            orderId = orderId,
            paymentTransactionId = paymentTransactionId,
            timestamp = timestamp
        )
    }


}