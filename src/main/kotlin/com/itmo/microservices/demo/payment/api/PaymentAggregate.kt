package com.itmo.microservices.demo.payment.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate

@AggregateType(aggregateEventsTableName = "payments")
class PaymentAggregate : Aggregate