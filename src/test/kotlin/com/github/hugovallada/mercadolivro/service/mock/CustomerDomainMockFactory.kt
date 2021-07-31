package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain

class CustomerDomainMockFactory {

    fun buildValidCustomerDomain() = CustomerDomain(
            name = "Customer", email = "customer@email.com"
    )

    fun buildValidCustomerDomainFull() = CustomerDomain(
            id = 1, name = "Customer", email = "customer@email.com"
    )

}