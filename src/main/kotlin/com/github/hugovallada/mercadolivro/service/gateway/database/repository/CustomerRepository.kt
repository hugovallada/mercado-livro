package com.github.hugovallada.mercadolivro.service.gateway.database.repository

import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<CustomerDB, Long> {

    fun existsByEmail(email: String) : Boolean

}