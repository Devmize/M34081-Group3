package com.itmo.microservices.demo.catalog.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "catalog-aggregate")
class CatalogAggregate: Aggregate {
}