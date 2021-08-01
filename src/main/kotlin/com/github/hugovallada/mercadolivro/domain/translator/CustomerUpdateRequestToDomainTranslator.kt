package com.github.hugovallada.mercadolivro.domain.translator

import com.github.hugovallada.mercadolivro.client.model.CustomerUpdateRequest
import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain

class CustomerUpdateRequestToDomainTranslator {

    fun translate(updateRequest: CustomerUpdateRequest) = CustomerDomain(
            name = updateRequest.name, email = updateRequest.email
    )

}