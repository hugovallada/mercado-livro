package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

class CustomerDBMockFactory {

    fun buildValidCustomerDB() = CustomerDB(
            id = 1, name = "Customer", email = "customer@email.com"
    )

    fun buildAPageableOfCustomerDB() : Page<CustomerDB> = PageImpl(listOf(buildValidCustomerDB()))


}