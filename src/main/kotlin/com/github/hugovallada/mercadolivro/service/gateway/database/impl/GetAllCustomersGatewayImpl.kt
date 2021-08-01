package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.GetAllCustomersGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class GetAllCustomersGatewayImpl(private val customerRepository: CustomerRepository) : GetAllCustomersGateway {

    override fun execute(page: Pageable): Page<CustomerDomain> {
        customerRepository.getAll(page).run {
            return CustomerDBToDomainTranslator().translate(this)
        }
    }

}