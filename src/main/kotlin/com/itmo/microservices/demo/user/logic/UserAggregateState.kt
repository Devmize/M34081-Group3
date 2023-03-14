package com.itmo.microservices.demo.user.logic

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.math.BigDecimal
import java.util.UUID

class UserAggregateState: AggregateState<UUID, UserAggregate> {
    private var userId: UUID = UUID.randomUUID()
    private var userName: String = ""
    private var surname: String? = ""
    private var address: String? = ""
    private var phoneNumber: BigDecimal? = BigDecimal.ZERO

    override fun getId(): UUID {
        return this.userId
    }

    fun getUserName(): String {
        return this.userName
    }

    fun createNewUser(userName: String): UserCreatedEvent {
        return UserCreatedEvent(userName);
    }

    @StateTransitionFunc
    fun createNewUser(event: UserCreatedEvent) {
        println(111111111)
        this.userName = event.userName;
    }
}