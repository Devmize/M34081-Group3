package com.itmo.microservices.demo.catalog.controller

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.api.CatalogCreatedEvent
import com.itmo.microservices.demo.catalog.api.ItemAddedToCatalogEvent
import com.itmo.microservices.demo.catalog.logic.CatalogAggregateState
import com.itmo.microservices.demo.catalog.logic.addItem
import com.itmo.microservices.demo.catalog.logic.createCatalog
import com.itmo.microservices.demo.catalog.model.AddItemRequest
import com.itmo.microservices.demo.catalog.model.CatalogItemDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*
import ru.quipy.core.EventSourcingService
import java.util.stream.Collectors


@RestController
@RequestMapping("/items")
class CatalogController(val catalogEsService: EventSourcingService<String, CatalogAggregate, CatalogAggregateState>) {


    @PostMapping("/create")
    fun createCatalog(): CatalogCreatedEvent {
        val e = catalogEsService.create {
            it.createCatalog()
        }
        return e
    }

    @PostMapping("/{catalogId}")
    fun addItem(@RequestBody item: AddItemRequest, @PathVariable catalogId: String): ItemAddedToCatalogEvent {
        // тут нуден не create а update, однако для апдейт как не странно нужен айди. Смотреть проблему выше
        val e: ItemAddedToCatalogEvent = catalogEsService.update(catalogId) {
            it.addItem(item.productId, item.productName, item.price, item.count, item.description)
        }
        return e
    }

    @GetMapping("/{catalogId}")
    fun getProducts(
        @RequestParam available: Boolean,
        @RequestParam size: Int,
        @PathVariable catalogId: String
    ): List<CatalogItemDto>? {
        // спросить почему не передается catalogId, спроектировано так, что каталог - агрегат
        // каталог оформлен как агрегат. чтобы получить конкретный агрегат нам нужен id, пусть агрегат всего один (singleton), однако у нас нет никакой логики по его созданию
        // поэтому мы его никак не сможем получить
        // тогда нам неоткуда брать этот самый список элементов
        val catalog = catalogEsService.getState(catalogId)
        if (catalog != null) {
            return catalog
                .products
                .values
                .stream()
                .limit(size.toLong())
                ?.map { x -> CatalogItemDto(x.productId!!, x.productName!!, x.description!!, x.price!!, x.count!!) }
                ?.collect(Collectors.toList())
        }
        return null
    }


}

