package com.github.hugovallada.mercadolivro.domain.translator

import com.github.hugovallada.mercadolivro.client.model.CustomerResponse
import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

class CustomerDomainToResponseTranslator {

    fun translate(customerDomain: CustomerDomain) = CustomerResponse(
            id = customerDomain.id!!, name = customerDomain.name, email = customerDomain.email
    )

    fun translate(pageOfDomains: Page<CustomerDomain>) : Page<CustomerResponse> {
        pageOfDomains.map {
            translate(it)
        }.run {
            return this
        }
    }

}