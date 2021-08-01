package com.github.hugovallada.mercadolivro.service.gateway.database

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetAllCustomersGateway {

    fun execute(page: Pageable) : Page<CustomerDomain>

}