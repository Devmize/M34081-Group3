package com.itmo.microservices.demo.delivery.entity

import javax.persistence.GeneratedValue
import javax.persistence.Id



class Delivery {

    @Id
    @GeneratedValue
    var id: String? = null
    var address: String? = null
    var userId: String? = null
    var deliverySlot: String? = null

    constructor()

    constructor(id: String, address: String, userId: String, deliverySlot: String) {
        this.id = id
        this.address = address
        this.userId = userId
        this.deliverySlot = deliverySlot
    }
}
