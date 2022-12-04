package com.itmo.microservices.demo.deliv.config

import com.itmo.microservices.demo.delivery.api.DeliveryAggregate
import com.itmo.microservices.demo.delivery.logic.DeliveryAggregateState
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.streams.AggregateEventStreamManager
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.UUID
import javax.annotation.PostConstruct

@Configuration
class DeliveryConfig {

    private val logger = LoggerFactory.getLogger(DeliveryConfig::class.java)

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    private lateinit var deliveryEventSubscriber: AnnotationBasedDeliveryEventSubscriber

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @PostConstruct
    fun init() {
        subscriptionsManager.subscribe<DeliveryAggregate>(deliveryEventSubscriber)

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
    fun userESService() = eventSourcingServiceFactory.create<UUID, DeliveryAggregate, DeliveryAggregateState>()
}