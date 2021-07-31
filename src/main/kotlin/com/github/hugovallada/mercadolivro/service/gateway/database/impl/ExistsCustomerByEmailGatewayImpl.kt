package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.ExistsCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import org.springframework.stereotype.Component

@Component
class ExistsCustomerByEmailGatewayImpl(private val customerRepository: CustomerRepository) : ExistsCustomerByEmailGateway {
    override fun execute(customerDomain: CustomerDomain): Boolean {
        return customerRepository.existsByEmail(customerDomain.email)
    }
}