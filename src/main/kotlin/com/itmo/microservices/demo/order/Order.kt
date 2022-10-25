package com.itmo.microservices.demo.order

import com.itmo.microservices.demo.product.Product
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Order {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var products: Array<Product>? = null
    var price: Double? = null
    var date: Date? = null

    constructor()

    constructor(id: UUID?, products: Array<Product>?, price: Double?, date: Date?) {
        this.id = id
        this.products = products
        this.price = price
        this.date = date
    }
}