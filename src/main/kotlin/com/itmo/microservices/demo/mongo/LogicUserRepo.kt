package com.itmo.microservices.demo.mongo

import com.itmo.microservices.demo.user.projections.UserViewDomain
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface LogicUserRepo : MongoRepository<UserViewDomain.User, UUID> {
    fun findByUserId(id: UUID): UserViewDomain.User?
}