package com.itmo.microservices.demo.mongo

import com.itmo.microservices.demo.payment.projections.PaymentViewDomain
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface FinlogRepo : MongoRepository<PaymentViewDomain.Payment, UUID> {
    fun findByPaymentId(id: UUID): PaymentViewDomain.Payment?
}