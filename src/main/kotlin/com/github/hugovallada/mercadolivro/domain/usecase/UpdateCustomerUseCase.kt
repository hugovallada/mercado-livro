package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.error.NotFoundException
import com.github.hugovallada.mercadolivro.service.gateway.database.ExistsCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.GetCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.PersistCustomerGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.UpdateCustomerGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class UpdateCustomerUseCase(private val existsCustomerByEmailGateway: ExistsCustomerByEmailGateway,
                            private val updateCustomerGateway: UpdateCustomerGateway) {

    fun execute(customerDomain: CustomerDomain){
        if(!existsCustomerByEmailGateway.execute(customerDomain))
            throw NotFoundException("Can't find customer with email ${customerDomain.email}")

        updateCustomerGateway.execute(customerDomain)
    }
}