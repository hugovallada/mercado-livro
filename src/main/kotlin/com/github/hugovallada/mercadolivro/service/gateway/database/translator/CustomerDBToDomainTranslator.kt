package com.github.hugovallada.mercadolivro.service.gateway.database.translator

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB
import org.springframework.data.domain.Page

class CustomerDBToDomainTranslator {

    fun translate(customerDB: CustomerDB) = CustomerDomain(
            name = customerDB.name,
            email = customerDB.email,
            id = customerDB.id
    )

    fun translate(pageOfDB: Page<CustomerDB>) : Page<CustomerDomain> {
        pageOfDB.map {
            translate(it)
        }.run {
            return this
        }
    }

}