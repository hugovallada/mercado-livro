package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.GetCustomerByEmailGateway
import org.springframework.stereotype.Component

@Component
class GetCustomerByEmailUseCase(private val getCustomerByEmailGateway: GetCustomerByEmailGateway) {

    fun execute(email: String) : CustomerDomain {
        return getCustomerByEmailGateway.execute(email)
    }

}