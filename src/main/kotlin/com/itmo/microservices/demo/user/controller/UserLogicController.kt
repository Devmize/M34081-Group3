package com.itmo.microservices.demo.user.controller

import com.itmo.microservices.demo.mongo.LogicUserRepo
import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.api.UserCreatedEvent
import com.itmo.microservices.demo.user.logic.UserAggregateState
import com.itmo.microservices.demo.user.model.UserDto
import com.itmo.microservices.demo.user.model.UserLogicCreateRequest
import com.itmo.microservices.demo.user.projections.UserViewDomain
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/usersLogic")
class UserLogicController(
    val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @GetMapping("/{userId}")
    fun getUsers(@PathVariable userId: UUID): UserDto? {
        val user = userEsService.getState(userId)
        if (user != null) {
            return UserDto(user.getId(), user.getUserName())
        }

        return UserDto(UUID.fromString("ccd3b1b1-456e-48b6-910e-5efbfa0653f7"), "name")
    }

    @PostMapping
    fun createUser(@RequestBody body: UserLogicCreateRequest): UserCreatedEvent {
        // при вызове метода create ниже пользователь не сохраняется в базу (монго) ни
        // монги (как у егора), ни во внутреннюю монгу фреймворка андрея (как при вызову например getState(id))
        // понять, как он сохраняется и как правильно поднимать монгу чтобы это работало
        return userEsService.create { it.createNewUser(userName = body.name) }
    }

}