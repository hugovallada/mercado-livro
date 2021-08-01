package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.UpdateCustomerGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import org.springframework.stereotype.Component

@Component
class UpdateCustomerGatewayImpl(private val customerRepository: CustomerRepository) : UpdateCustomerGateway{
    override fun execute(customerDomain: CustomerDomain) {
        customerRepository.updateName(customerDomain.email, customerDomain.name)
    }

}