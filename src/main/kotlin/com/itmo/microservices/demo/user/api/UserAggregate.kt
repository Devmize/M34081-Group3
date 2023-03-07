package com.itmo.microservices.demo.user.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "userLogic")
class UserAggregate: Aggregate {
}