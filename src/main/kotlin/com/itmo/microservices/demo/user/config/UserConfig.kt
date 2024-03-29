package com.itmo.microservices.demo.user.config

import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.logic.UserAggregateState
import com.itmo.microservices.demo.user.projections.AnnotationBasedUserEventSubscriber
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
class UserConfig {

    private val logger = LoggerFactory.getLogger(UserConfig::class.java)

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    private lateinit var userEventSubscriber: AnnotationBasedUserEventSubscriber

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    @PostConstruct
    fun init() {
        subscriptionsManager.subscribe<UserAggregate>(userEventSubscriber)

        eventStreamManager.maintenance {
            onRecordHandledSuccessfully { streamName, eventName ->
                logger.debug("Stream $streamName successfully processed record of $eventName")
            }

            onBatchRead { streamName, batchSize ->
                logger.debug("Stream $streamName read batch size: $batchSize")
            }
        }
    }

    @Bean
    fun userESService() = eventSourcingServiceFactory.create<UUID, UserAggregate, UserAggregateState>()
}