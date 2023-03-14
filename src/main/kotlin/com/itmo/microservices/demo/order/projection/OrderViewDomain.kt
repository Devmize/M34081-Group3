package com.itmo.microservices.demo.order.projection

import com.itmo.microservices.demo.order.api.model.OrderStatus
import com.itmo.microservices.demo.order.api.model.PaymentLogRecordModel
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

class OrderViewDomain {
    @Document("order-view")
    data class Order(
        var id: UUID,
        var status: OrderStatus,
        var itemsMap: Map<UUID, Double>,
        var timeCreated: Number,
        var deliveryDuration: Number,
        var paymentHistory: List<PaymentLogRecordModel>
    )
}