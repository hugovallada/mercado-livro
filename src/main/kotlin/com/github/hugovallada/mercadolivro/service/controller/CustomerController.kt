package com.github.hugovallada.mercadolivro.service.controller

import com.github.hugovallada.mercadolivro.client.api.CustomerApi
import com.github.hugovallada.mercadolivro.client.model.CustomerRequest
import com.github.hugovallada.mercadolivro.client.model.CustomerResponse
import com.github.hugovallada.mercadolivro.domain.translator.CustomerDomainToResponseTranslator
import com.github.hugovallada.mercadolivro.domain.translator.CustomerRequestToDomainTranslator
import com.github.hugovallada.mercadolivro.domain.usecase.CreateCustomerUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class CustomerController(private val createCustomerUseCase: CreateCustomerUseCase) : CustomerApi {

    override fun createCustomer(request: CustomerRequest): CustomerResponse {
        val domain = CustomerRequestToDomainTranslator().translate(request)
        createCustomerUseCase.execute(domain).run {
            return CustomerDomainToResponseTranslator().translate(this)
        }
    }
}