/*package com.itmo.microservices.demo.deliv.logic

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

class DeliveryAggregateState : AggregateState<UUID, DeliveryAggregate> {
    private lateinit var DeliveryId: UUID
    private var timeSlot: Timestamp = Timestamp(0, 0, 0, 0, 0, 0, 0)
    private var address: String? = ""
    private var phoneNumber: String? = ""

    override fun getId(): UUID {
        return this.DeliveryId
    }

    fun startToDeliver(time: Timestamp, address_: String, number: String) {
        timeSlot = time
        address = address_
        phoneNumber = number
    }

    fun endeedDelivery() {
        address = ""
        timeSlot = Timestamp(0, 0, 0, 0, 0, 0, 0)
    }
}
 */