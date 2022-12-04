package com.itmo.microservices.demo.user.projections;

import com.itmo.microservices.demo.user.api.UserAggregate;
import org.springframework.stereotype.Service;
import ru.quipy.streams.annotation.AggregateSubscriber;

@Service
@AggregateSubscriber(aggregateClass = UserAggregate.class, subscriberName = "user-subs-stream")
public class AnnotationBasedUserEventSubscriber {
}
