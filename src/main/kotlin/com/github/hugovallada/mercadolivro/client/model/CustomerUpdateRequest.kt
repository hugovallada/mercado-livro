package com.github.hugovallada.mercadolivro.client.model

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CustomerUpdateRequest(
        @field:Email @field:NotBlank
        val email: String,
        @field:NotBlank
        val name: String
)
