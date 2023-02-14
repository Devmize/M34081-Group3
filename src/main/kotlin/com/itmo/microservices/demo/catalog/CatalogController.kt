package com.itmo.microservices.demo.catalog

import com.itmo.microservices.demo.catalog.api.CatalogAggregate
import com.itmo.microservices.demo.catalog.logic.CatalogAggregateState
import com.itmo.microservices.demo.catalog.model.CatalogItemDto
import com.itmo.microservices.demo.user.api.UserAggregate
import com.itmo.microservices.demo.user.logic.UserAggregateState
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.quipy.core.EventSourcingService
import java.util.*

@RestController
@RequestMapping("/catalog")
class CatalogController(val catalogEsService: EventSourcingService<String, CatalogAggregate, CatalogAggregateState>) {

//    @GetMapping("/items")
//    fun getItems(@RequestParam("available") available: Boolean, @RequestParam("size") size: Integer): List<CatalogItemDto> {
//
////        return catalogEsService.getState()?.products?.values.toList().stream().limit(size.toLong()).map { order -> CatalogItemDto(title = order.) }
//    }
}