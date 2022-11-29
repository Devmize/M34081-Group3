package com.itmo.microservices.demo.user.model

import java.util.*

data class UserDto(
    val id: UUID,
    val name: String
)