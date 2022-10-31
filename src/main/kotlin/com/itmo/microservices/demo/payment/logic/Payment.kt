package com.itmo.microservices.demo.payment.logic

import com.fasterxml.jackson.databind.ObjectMapper
import com.itmo.microservices.demo.payment.api.PaymentAggregate
import com.itmo.microservices.demo.payment.api.PaymentAttemptEvent
import ru.quipy.domain.AggregateState
import ru.quipy.domain.Event
import java.math.BigDecimal
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

class Payment : AggregateState<UUID, PaymentAggregate> {
    private lateinit var paymentId: UUID
    private lateinit var orderId: UUID
    private lateinit var sum: BigDecimal

    override fun getId(): UUID {
        return paymentId
    }

    fun tryToPay(orderId: UUID, sum: BigDecimal): Event<PaymentAggregate> {

        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper
            .writeValueAsString(values)

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://77.234.215.138:30027/transactions/payment"))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        response.body()


        return PaymentAttemptEvent(
            paymentId = paymentId,
            orderId = orderId,
            sum = sum
        )
    }

}