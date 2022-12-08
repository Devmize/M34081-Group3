package com.itmo.microservices.demo.deliv.projections

import com.itmo.microservices.demo.deliv.api.DeliveryAggregate
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class DeliveryEventSubscriber {

    val logger: Logger = LoggerFactory.getLogger(DeliveryEventsSubscriber::class.java)

    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(DeliveryAggregate::class, "name") {

            `when`(DeliveryAttemptEvent::class) { event ->
                logger.info(
                    "attempt to delivery the order: time {}, phone {}, idnum {}",
                    event.time, event.phoneNum, event.idnum
                )
            }
            `when`(DeliveryCompletedSuccessfullyEvent::class) { event ->
                logger.info(
                    "dalivery completed successfully: time {}, idnum {}",
                    event.time, event.idnum
                )
            }
            `when`(DeliveryFailedEvent::class) { event ->
                logger.info(
                    "delivery failed: time {}, idnum {}",
                    event.time, event.idnum
                )
            }
        }
    }
}