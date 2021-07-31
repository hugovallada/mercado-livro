package com.github.hugovallada.mercadolivro.service.gateway.database.model

import javax.persistence.*

@Entity
@Table(name = "customer")
class CustomerDB(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String,
        val email: String
)