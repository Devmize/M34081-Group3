package com.itmo.microservices.demo.user.projections

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

class UserViewDomain {
    @Document("user-view")
    data class User(
        val userId: UUID,
        val userName: String,
        val surname: String,
        val address: String,
        val phoneNumber: BigDecimal
    )
}