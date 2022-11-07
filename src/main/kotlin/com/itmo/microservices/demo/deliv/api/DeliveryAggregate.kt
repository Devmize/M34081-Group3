package com.itmo.microservices.demo.delivery.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "delivery-aggregate")
class DeliveryAggregate: Aggregate {
}