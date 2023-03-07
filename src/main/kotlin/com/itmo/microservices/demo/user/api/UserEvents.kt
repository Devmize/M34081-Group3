package com.itmo.microservices.demo.user.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event

const val USER_CREATED_EVENT = "USER_CREATED_EVENT"

@DomainEvent(name = USER_CREATED_EVENT)
class UserCreatedEvent(
    val userName: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<UserAggregate>(
    name = USER_CREATED_EVENT,
    createdAt = createdAt
)