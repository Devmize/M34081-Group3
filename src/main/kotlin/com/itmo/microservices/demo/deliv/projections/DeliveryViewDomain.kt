
package com.itmo.microservices.demo.payment.projections

import com.itmo.microservices.demo.deliv.api.DeliveryStatus
import java.sql.Timestamp
import java.util.*

class DeliveryViewDomain {
//    @Document("payment-view")
    data class Payment(
    val deliveryId: UUID,
    val timestamp: Timestamp,
    val status: DeliveryStatus,
    val address: String,
    val phoneNumber: String,
    )
}
