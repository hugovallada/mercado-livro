package com.github.hugovallada.mercadolivro.domain.translator

import com.github.hugovallada.mercadolivro.client.model.CustomerResponse
import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain

class CustomerDomainToResponseTranslator {

    fun translate(customerDomain: CustomerDomain) = CustomerResponse(
            id = customerDomain.id!!, name = customerDomain.name, email = customerDomain.email
    )

}