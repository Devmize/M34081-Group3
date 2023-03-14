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
class UserLogicController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @GetMapping("{userId}")
    fun getUsers(@PathVariable userId: UUID): UserAggregateState? {
        val e: UserAggregateState? = userEsService.getState(userId)
        println(e)
        return e
    }

    @PostMapping
    fun createUser(@RequestBody body: UserLogicCreateRequest): UserCreatedEvent {
        // при вызове метода create ниже пользователь не сохраняется в базу (монго) ни
        // монги (как у егора), ни во внутреннюю монгу фреймворка андрея (как при вызову например getState(id))
        // понять, как он сохраняется и как правильно поднимать монгу чтобы это работало
        val e: UserCreatedEvent = userEsService.create { it.createNewUser(userName = body.name) }
        println(e)
        return e
    }

}