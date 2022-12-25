package com.itmo.microservices.demo.deliv.logic

import java.sql.Timestamp
import java.util.*

class DeliveryAggregateCommand {
    private lateinit var deliveryId: UUID
    private var timeSlot: Timestamp = Timestamp(0, 0, 0, 0, 0, 0, 0);
    private var address: String? = ""
    private var phoneNumber: String? = ""
}