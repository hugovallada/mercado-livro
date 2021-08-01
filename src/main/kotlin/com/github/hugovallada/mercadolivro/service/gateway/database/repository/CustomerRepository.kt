package com.github.hugovallada.mercadolivro.service.gateway.database.repository

import com.github.hugovallada.mercadolivro.domain.domain.CustomerDomain
import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<CustomerDB, Long> {

    fun existsByEmail(email: String) : Boolean

    @Query("Select * from customer", nativeQuery = true)
    fun getAll(pageable: Pageable) : Page<CustomerDB>

}