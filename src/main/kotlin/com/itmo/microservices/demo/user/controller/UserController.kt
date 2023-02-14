package com.itmo.microservices.demo.user.controller;

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import com.itmo.microservices.demo.user.logic.UserAggregateState
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>) {

    @GetMapping("{userId}")
    fun getUsers(@PathVariable userId: UUID): UserAggregateState? {
        return userEsService.getState(userId)
    }

    @PostMapping
    fun createUser(@RequestBody name: String): UserCreatedEvent {
        return userEsService.create { it.createNewUser(name = name) }
    }

}
