package com.github.hugovallada.mercadolivro.service.gateway.database.translator

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB

class CustomerDomainToDBTranslator {

    fun translate(customerDomain: CustomerDomain) = CustomerDB(
            name = customerDomain.name, email = customerDomain.email
    )

}