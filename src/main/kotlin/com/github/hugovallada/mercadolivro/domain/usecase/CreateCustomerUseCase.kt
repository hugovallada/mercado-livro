package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.error.AlreadyExistsException
import com.github.hugovallada.mercadolivro.service.gateway.database.ExistsCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.PersistCustomerGateway
import org.springframework.stereotype.Component

@Component
class CreateCustomerUseCase(private val persistCustomerGateway: PersistCustomerGateway,
                            private val existsCustomerByEmailGateway: ExistsCustomerByEmailGateway) {

    fun execute(customerDomain: CustomerDomain) : CustomerDomain {
        if(existsCustomerByEmailGateway.execute(customerDomain)) {
            throw AlreadyExistsException("There's already a customer with this email")
        }
        return persistCustomerGateway.execute(customerDomain)
    }

}