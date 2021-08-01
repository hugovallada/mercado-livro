package com.github.hugovallada.mercadolivro.service.controller

import com.github.hugovallada.mercadolivro.client.api.CustomerApi
import com.github.hugovallada.mercadolivro.client.model.CustomerRequest
import com.github.hugovallada.mercadolivro.client.model.CustomerResponse
import com.github.hugovallada.mercadolivro.domain.translator.CustomerDomainToResponseTranslator
import com.github.hugovallada.mercadolivro.domain.translator.CustomerRequestToDomainTranslator
import com.github.hugovallada.mercadolivro.domain.usecase.CreateCustomerUseCase
import com.github.hugovallada.mercadolivro.domain.usecase.GetAllCustomersUseCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
class CustomerController(private val createCustomerUseCase: CreateCustomerUseCase,
                         private val getAllCustomersUseCase: GetAllCustomersUseCase) : CustomerApi {

    override fun createCustomer(request: CustomerRequest): CustomerResponse {
        val domain = CustomerRequestToDomainTranslator().translate(request)
        createCustomerUseCase.execute(domain).run {
            return CustomerDomainToResponseTranslator().translate(this)
        }
    }

    override fun getAllCustomers(pageable: Pageable): Page<CustomerResponse> {
        getAllCustomersUseCase.execute(pageable).run {
            return CustomerDomainToResponseTranslator().translate(this)
        }
    }
}