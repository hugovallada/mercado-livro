package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.error.NotFoundException
import com.github.hugovallada.mercadolivro.service.gateway.database.GetCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import org.springframework.stereotype.Component

@Component
class GetCustomerByEmailGatewayImpl (private val customerRepository: CustomerRepository) : GetCustomerByEmailGateway {

    override fun execute(email: String) : CustomerDomain {
        customerRepository.findByEmail(email).run {
            return CustomerDBToDomainTranslator().translate(this ?: throw NotFoundException(
                    "Can't find customer with email $email"
            ))
        }
    }
}