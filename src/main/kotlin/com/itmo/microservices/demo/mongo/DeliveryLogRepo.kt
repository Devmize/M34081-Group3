package com.itmo.microservices.demo.mongo

import com.itmo.microservices.demo.delivery.projections.DeliveryViewDomain
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface DeliveryLogRepo : MongoRepository<DeliveryViewDomain.Delivery, UUID> {
    fun findByTransactionId(id: UUID): DeliveryViewDomain.Delivery?
}