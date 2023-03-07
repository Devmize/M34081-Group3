package com.itmo.microservices.demo.user.logic

import com.itmo.microservices.demo.user.api.UserAggregate
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.util.UUID

class UserAggregateState: AggregateState<UUID, UserAggregate> {
    private lateinit var userId: UUID
    private var userName: String? = ""
    private var surname: String? = ""
    private var address: String? = ""
    private var phoneNumber: BigDecimal? = BigDecimal.ZERO

    override fun getId(): UUID {
        return this.userId
    }
}