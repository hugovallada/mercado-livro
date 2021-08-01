package com.github.hugovallada.mercadolivro.service.gateway.database.repository

import com.github.hugovallada.mercadolivro.service.gateway.database.model.CustomerDB
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<CustomerDB, Long> {

    fun existsByEmail(email: String) : Boolean

    @Query("Select * from customer", nativeQuery = true)
    fun getAll(pageable: Pageable) : Page<CustomerDB>

    fun findByEmail(email: String) : CustomerDB?

    @Modifying
    @Query("Update CustomerDB c SET c.name = ?2 where c.email = ?1")
    fun updateName(email: String, name: String)

}