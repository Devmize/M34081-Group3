/*package com.itmo.microservices.demo.catalog.config

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.logic.CatalogAggregateState
import com.itmo.microservices.demo.catalog.projections.AnnotationBasedCatalogEventsSubscriber
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.quipy.core.AggregateRegistry
import ru.quipy.core.EventSourcingServiceFactory
import ru.quipy.streams.AggregateEventStreamManager
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Configuration
class CatalogConfig {

    private val logger = LoggerFactory.getLogger(CatalogConfig::class.java)

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    // todo
    private lateinit var catalogEventSubscriber: AnnotationBasedCatalogEventsSubscriber

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @Autowired
    private lateinit var aggregateRegistry: AggregateRegistry

    @PostConstruct
    fun init() {

        subscriptionsManager.subscribe<CatalogAggregate>(catalogEventSubscriber)

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
    fun catalogESService() = eventSourcingServiceFactory.create<String, CatalogAggregate, CatalogAggregateState>()
}*/