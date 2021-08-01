package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.client.model.CustomerRequest

class CustomerRequestMockFactory {

    fun buildValidCustomerRequest() = CustomerRequest(
            name = "Customer", email = "customer@email.com"
    )

    fun buildInvalidCustomerRequest() = CustomerRequest(
            name = "", email = "customer"
    )

}