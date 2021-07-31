package com.github.hugovallada.mercadolivro.domain.translator

import com.github.hugovallada.mercadolivro.client.model.CustomerRequest
import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain

class CustomerRequestToDomainTranslator {

    fun translate(request: CustomerRequest) = CustomerDomain(
            name = request.name, email = request.email
    )

}