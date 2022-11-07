package com.itmo.microservices.demo.delivery.projections;

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate;
import org.springframework.stereotype.Service;
import ru.quipy.streams.annotation.AggregateSubscriber;

@Service
@AggregateSubscriber(aggregateClass = DeliveryAggregate.class, subscriberName = "user-subs-stream")
public class AnnotationBasedDeliveryEventSubscriber {
}