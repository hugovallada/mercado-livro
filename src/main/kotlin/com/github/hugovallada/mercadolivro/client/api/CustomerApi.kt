package com.github.hugovallada.mercadolivro.client.api

import com.github.hugovallada.mercadolivro.client.model.CustomerRequest
import com.github.hugovallada.mercadolivro.client.model.CustomerResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import javax.validation.Valid

@RequestMapping("/api/v1/customers")
interface CustomerApi {

    @ApiOperation(value = "Creates new customer", notes = "Creates new customer on the database")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = CustomerResponse::class),
        ApiResponse(code = 400, message = "Bad Request"),
        ApiResponse(code = 422, message = "Already Exists"),
        ApiResponse(code = 500, message = "Internal Server Error")
    ])
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createCustomer(@Valid @RequestBody request : CustomerRequest) : CustomerResponse


}