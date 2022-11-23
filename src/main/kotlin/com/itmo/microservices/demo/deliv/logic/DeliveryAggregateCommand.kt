package com.itmo.microservices.demo.deliv.logic

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.UUID

class DeliveryAggregateCommand {

    fun startToDeliver(Timestamp time, String address_, BigDecimal number) {
        timeSlot = time;
        address = address_;
        phoneNumber = number;
    }

    fun endeedDelivery() {
        address = "";
        timeSlot = Timestamp(0,0,0,0);

    }

    private lateinit var deliveryId: UUID
    private var timeSlot: Timestamp = Timestamp(0,0,0,0,0);
    private var address: String? = ""
    private var phoneNumber: String? = ""

    override fun getId(): UUID {
        return this.deliveryId
    }



}