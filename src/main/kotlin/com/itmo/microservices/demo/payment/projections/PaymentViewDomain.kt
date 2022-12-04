package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.payment.api.PaymentStatus
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class PaymentViewDomain {
    @Document("payment-view")
    data class Payment(
        val paymentId: UUID,
        val orderId: UUID,
        val amount: Int,
        val status: PaymentStatus,
    )
}