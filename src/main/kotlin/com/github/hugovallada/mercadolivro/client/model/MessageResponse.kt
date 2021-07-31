package com.github.hugovallada.mercadolivro.client.model

data class MessageResponse(
        val code : Int,
        val message: String,
        val errors : List<String>? = null
)