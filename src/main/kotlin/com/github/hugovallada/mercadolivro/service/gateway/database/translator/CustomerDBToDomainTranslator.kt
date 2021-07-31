package com.github.hugovallada.mercadolivro.service.gateway.database.translator

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB

class CustomerDBToDomainTranslator {

    fun translate(customerDB: CustomerDB) = CustomerDomain(
            name = customerDB.name,
            email = customerDB.email,
            id = customerDB.id
    )

}