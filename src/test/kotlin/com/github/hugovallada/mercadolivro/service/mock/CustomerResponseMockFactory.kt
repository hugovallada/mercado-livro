package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.client.model.CustomerResponse

class CustomerResponseMockFactory {

    fun buildValidCustomerResponse() = CustomerResponse(
            id = 1, email = "email@email.com", name = "Customer"
    )

}