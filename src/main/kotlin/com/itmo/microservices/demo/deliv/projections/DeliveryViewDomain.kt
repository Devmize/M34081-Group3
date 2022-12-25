
package com.itmo.microservices.demo.deliv.projections

import com.itmo.microservices.demo.catalog.logic.Order
import com.itmo.microservices.demo.catalog.logic.Product
import com.itmo.microservices.demo.deliv.api.DeliveryStatus
import java.sql.Timestamp
import java.util.*

class DeliveryViewDomain {
//    @Document("payment-view")
    data class Delivery(
    val outcome: Long,
    val preparedTime: Long,
    val attempts: Int,
    val submittedTime: Long,
    val transactionId: UUID,
    val submissionStartedTime: Long
)
}
