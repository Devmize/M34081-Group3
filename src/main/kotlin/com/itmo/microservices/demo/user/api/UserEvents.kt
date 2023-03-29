package com.itmo.microservices.demo.user.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val USER_CREATED_EVENT = "USER_CREATED_EVENT"

@DomainEvent(name = USER_CREATED_EVENT)
data class UserCreatedEvent(
    val userId: UUID,
    val userName: String,
) : Event<UserAggregate>(
    name = USER_CREATED_EVENT,
)