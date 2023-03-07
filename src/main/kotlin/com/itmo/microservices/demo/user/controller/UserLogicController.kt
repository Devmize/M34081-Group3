package com.itmo.microservices.demo.user.controller

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import com.itmo.microservices.demo.user.logic.UserAggregateState
import com.itmo.microservices.demo.user.model.UserLogicCreateRequest
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/usersLogic")
class UserLogicController(val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>) {


    @GetMapping("{userId}")
    fun getUsers(@PathVariable userId: UUID): UserAggregateState? {
        return userEsService.getState(userId)
    }

    @PostMapping
    fun createUser(@RequestBody body: UserLogicCreateRequest): UserCreatedEvent {
        val e: UserCreatedEvent = userEsService.create { it.createNewUser(userName = body.name) }
        println(userEsService.getState(e.id))
        return e

    }

}