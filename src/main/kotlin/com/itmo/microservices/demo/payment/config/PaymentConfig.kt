package com.itmo.microservices.demo.payment.config

import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.logic.PaymentAggregateState
import com.itmo.microservices.demo.payment.projections.AnnotationBasedPaymentEventsSubscriber
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
class PaymentConfig {
    @Autowired
    private lateinit var eventSourcingServiceFactory: EventSourcingServiceFactory

    private val logger = LoggerFactory.getLogger(PaymentConfig::class.java)

    @Autowired
    private lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    private lateinit var paymentEventsSubscriber: AnnotationBasedPaymentEventsSubscriber

    @Autowired
    private lateinit var eventStreamManager: AggregateEventStreamManager

    @PostConstruct
    fun init() {

        subscriptionsManager.subscribe<PaymentAggregate>(paymentEventsSubscriber)

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
    fun paymentESService(): EventSourcingService<UUID, PaymentAggregate, PaymentAggregateState> =
        eventSourcingServiceFactory.create()
}