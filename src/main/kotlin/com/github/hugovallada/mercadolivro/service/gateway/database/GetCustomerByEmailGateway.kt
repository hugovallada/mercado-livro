package com.github.hugovallada.mercadolivro.service.gateway.database

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain

interface GetCustomerByEmailGateway {

    fun execute(email: String) : CustomerDomain

}