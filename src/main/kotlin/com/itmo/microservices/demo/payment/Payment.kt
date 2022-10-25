package com.itmo.microservices.demo.payment

import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Payment {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var orderId: UUID? = null
    var sum: BigDecimal? = null
    var date: Date? = null

    constructor()

    constructor(id: UUID?, orderId: UUID?, sum: BigDecimal?, date: Date?) {
        this.id = id
        this.orderId = orderId
        this.sum = sum
        this.date = date
    }
}