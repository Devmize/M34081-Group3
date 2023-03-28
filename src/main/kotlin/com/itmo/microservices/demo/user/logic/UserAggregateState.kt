package com.itmo.microservices.demo.user.logic

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.util.UUID

class UserAggregateState: AggregateState<UUID, UserAggregate> {
    private lateinit var id: UUID
    private lateinit var userName: String
    lateinit var surname: String
    lateinit var address: String
    lateinit var phoneNumber: BigDecimal

    override fun getId(): UUID {
        return this.id
    }

    fun getUserName(): String {
        return this.userName
    }

    fun createNewUser(id: UUID, userName: String): UserCreatedEvent {

        return UserCreatedEvent(id, userName);
    }

    @StateTransitionFunc
    fun createNewUser(event: UserCreatedEvent) {
        this.id = event.id
        this.userName = event.userName;
    }
}