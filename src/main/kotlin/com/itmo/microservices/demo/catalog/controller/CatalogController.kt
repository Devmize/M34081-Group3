package com.itmo.microservices.demo.catalog.controller

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.api.ItemAddedToCatalogEvent
import com.itmo.microservices.demo.catalog.logic.CatalogAggregateState
import com.itmo.microservices.demo.catalog.logic.Order
import com.itmo.microservices.demo.catalog.logic.Product
import com.itmo.microservices.demo.catalog.logic.addItem
import com.itmo.microservices.demo.catalog.model.AddItemRequest
import com.itmo.microservices.demo.catalog.model.CatalogItemDto
import com.itmo.microservices.demo.catalog.projections.CatalogViewDomain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.UUID
import java.util.stream.Collectors


@RestController
@RequestMapping("/items")
class CatalogController(val catalogEsService: EventSourcingService<String, CatalogAggregate, CatalogAggregateState>) {

    @GetMapping
    fun getProducts(@RequestParam available: Boolean, @RequestParam size: Int): List<CatalogItemDto>? {
        // спросить почему не передается catalogId, спроектировано так, что каталог - агрегат
        // каталог оформлен как агрегат. чтобы получить конкретный агрегат нам нужен id, пусть агрегат всего один (singleton), однако у нас нет никакой логики по его созданию
        // поэтому мы его никак не сможем получить
        // тогда нам неоткуда брать этот самый список элементов
        val catalog: CatalogViewDomain.Catalog = CatalogViewDomain.Catalog(UUID.randomUUID().toString(), emptyMap<UUID, Product>(), emptyMap<UUID, Order>())
        return catalog
            .products
            .values
            .stream()
            .limit(size.toLong())
            ?.map {x -> CatalogItemDto(x.productId!!, x.productName!!, x.description!!, x.price!!, x.count!!)}
            ?.collect(Collectors.toList())
    }

    @PostMapping
    fun addItem(@RequestBody item: AddItemRequest): ItemAddedToCatalogEvent {
        // тут нуден не create а update, однако для апдейт как не странно нужен айди. Смотреть проблему выше
        val e: ItemAddedToCatalogEvent = catalogEsService.create {
            it.addItem(item.productId, item.productName, item.price, item.count, item.description)
        }
        return e
    }
}

