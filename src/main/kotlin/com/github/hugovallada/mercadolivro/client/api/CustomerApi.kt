package com.github.hugovallada.mercadolivro.client.api

import com.github.hugovallada.mercadolivro.client.model.CustomerRequest
import com.github.hugovallada.mercadolivro.client.model.CustomerResponse
import com.github.hugovallada.mercadolivro.client.model.MessageResponse
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.aspectj.bridge.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/v1/customers")
interface CustomerApi {

    @ApiOperation(value = "Creates new customer", notes = "Creates new customer on the database")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Created", response = CustomerResponse::class),
        ApiResponse(code = 400, message = "Bad Request", response = MessageResponse::class),
        ApiResponse(code = 422, message = "Already Exists", response = MessageResponse::class),
        ApiResponse(code = 500, message = "Internal Server Error", response = MessageResponse::class)
    ])
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createCustomer(@Valid @RequestBody request : CustomerRequest) : CustomerResponse

    @ApiOperation(value = "Get all customers", notes = "Get all customers")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "OK", response = CustomerResponse::class, responseContainer = "Page"),
        ApiResponse(code = 500, message = "Internal Server Error", response = MessageResponse::class)
    ])
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    fun getAllCustomers(@PageableDefault(size = 5) pageable: Pageable) : Page<CustomerResponse>


}