package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.PersistCustomerGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDomainToDBTranslator
import org.springframework.stereotype.Component

@Component
class PersistCustomerGatewayImpl(private val customerRepository: CustomerRepository) : PersistCustomerGateway{

    override fun execute(customerDomain: CustomerDomain): CustomerDomain {
        val customerToBePersisted = CustomerDomainToDBTranslator().translate(customerDomain)
        val customerPersisted = customerRepository.save(customerToBePersisted)
        return CustomerDBToDomainTranslator().translate(customerPersisted)
    }


}