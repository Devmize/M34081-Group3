package com.itmo.microservices.demo.product

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Product {

    @Id
    @GeneratedValue
    var id: UUID? = null
    var name: String? = null
    var price: Double? = null
    var count: Int? = null
    var description: String? = null

    constructor()

    constructor(id: UUID?, name: String?, price: Double?, count: Int?, description: String?) {
        this.id = id
        this.name = name
        this.price = price
        this.count = count
        this.description = description
    }
}