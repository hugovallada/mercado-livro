package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB

class CustomerDBMockFactory {

    fun buildValidCustomerDB() = CustomerDB(
            id = 1, name = "Customer", email = "customer@email.com"
    )

}