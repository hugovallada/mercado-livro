package com.github.hugovallada.mercadolivro.service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hugovallada.mercadolivro.domain.usecase.CreateCustomerUseCase
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import com.github.hugovallada.mercadolivro.service.mock.CustomerRequestMockFactory
import com.ninjasquad.springmockk.MockkBean
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.StandardCharsets.UTF_8

@ExtendWith(MockKExtension::class)
@WebMvcTest(CustomerController::class)
class CreateControllerTest {

    @MockkBean
    private lateinit var createCustomerUseCase: CreateCustomerUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val apiPath = "/api/v1/customers"

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a status 201 with a customer response`() {
        val request = CustomerRequestMockFactory().buildValidCustomerRequest()
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomainFull()

        every { createCustomerUseCase.execute(any()) } returns customerDomain

        mockMvc.perform(post(apiPath)
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name())
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated)
                .andDo(print())
                .andReturn().run {
                    Assertions.assertEquals(mapper.writeValueAsString(customerDomain), response.contentAsString)
                }

        verify(exactly = 1) { createCustomerUseCase.execute(any()) }
    }
}