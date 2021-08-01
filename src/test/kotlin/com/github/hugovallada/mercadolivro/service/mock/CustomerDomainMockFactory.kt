package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault

class CustomerDomainMockFactory {

    fun buildValidCustomerDomain() = CustomerDomain(
            name = "Customer", email = "customer@email.com"
    )

    fun buildValidCustomerDomainFull() = CustomerDomain(
            id = 1, name = "Customer", email = "customer@email.com"
    )

    fun buildValidPageableOfCustomerDomainFull() : Page<CustomerDomain> = PageImpl(listOf(buildValidCustomerDomainFull()))


}