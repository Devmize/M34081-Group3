package com.itmo.microservices.demo.catalog.controller

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.logic.CatalogAggregateState
import com.itmo.microservices.demo.catalog.model.CatalogItemDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.stream.Collectors


@RestController
@RequestMapping("/items")
class CatalogController(val catalogEsService: EventSourcingService<String, CatalogAggregate, CatalogAggregateState>) {

    @GetMapping
    fun getProducts(@RequestParam available: Boolean, @RequestParam size: Int): List<CatalogItemDto>? {
        // спросить почему не передается catalogId, спроектировано так, что каталог - агрегат
        return catalogEsService
            .getState("")
            ?.products
            ?.values
            ?.stream()
            ?.limit(size.toLong())
            ?.map {x -> CatalogItemDto(x.productId!!, x.productName!!, x.description!!, x.price!!, x.count!!)}
            ?.collect(Collectors.toList())
    }
}

