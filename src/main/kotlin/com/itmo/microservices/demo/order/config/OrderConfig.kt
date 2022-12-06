package com.itmo.microservices.demo.order.config

import com.itmo.microservices.demo.order.api.OrderAggregate
import com.itmo.microservices.demo.order.logic.Order
import com.itmo.microservices.demo.order.projection.AnnotationBasedOrderEventsSubscriber
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingService
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.streams.AggregateEventStreamManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Configuration
class OrderConfig {

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    private val logger = LoggerFactory.getLogger(OrderConfig::class.java)

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    private lateinit var orderEventSubscriber: AnnotationBasedOrderEventsSubscriber

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @PostConstruct
    fun init() {

        subscriptionsManager.subscribe<OrderAggregate>(orderEventSubscriber)

        eventStreamManager.maintenance {
            onRecordHandledSuccessfully { streamName, eventName ->
                logger.info("Stream $streamName successfully processed record of $eventName")
            }

            onBatchRead { streamName, batchSize ->
                logger.info("Stream $streamName read batch size: $batchSize")
            }
        }
    }
    @Bean
    fun orderESService(): EventSourcingService<UUID, OrderAggregate, Order> {
        return eventSourcingServiceFactory.create()
    }
}