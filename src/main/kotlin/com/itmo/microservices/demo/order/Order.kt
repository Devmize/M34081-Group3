package com.itmo.microservices.demo.order

import com.itmo.microservices.demo.catalog.Product
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id

class Order {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var orderItems: Array<Product>? = null
    var price: Double? = null
    var date: Date? = null

    constructor()

    constructor(id: UUID?, products: Array<Product>?, price: Double?, date: Date?) {
        this.id = id
        this.orderItems = products
        this.price = price
        this.date = date
    }
}