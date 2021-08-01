package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.GetAllCustomersGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.impl.GetAllCustomersGatewayImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class GetAllCustomersUseCase(private val getAllCustomersGateway: GetAllCustomersGateway) {

    fun execute(pageable: Pageable) : Page<CustomerDomain> {
        return getAllCustomersGateway.execute(pageable)
    }

}