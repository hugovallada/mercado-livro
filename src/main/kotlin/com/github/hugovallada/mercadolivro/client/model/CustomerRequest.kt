package com.github.hugovallada.mercadolivro.client.model

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class CustomerRequest(
        @ApiModelProperty(required = true)
        @field:NotBlank
        val name: String,

        @ApiModelProperty(required = true)
        @field:Email
        val email: String
)
