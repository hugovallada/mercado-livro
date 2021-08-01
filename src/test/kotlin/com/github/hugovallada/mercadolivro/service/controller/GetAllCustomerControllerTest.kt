package com.github.hugovallada.mercadolivro.service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hugovallada.mercadolivro.domain.translator.CustomerDomainToResponseTranslator
import com.github.hugovallada.mercadolivro.domain.usecase.CreateCustomerUseCase
import com.github.hugovallada.mercadolivro.domain.usecase.GetAllCustomersUseCase
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import com.github.hugovallada.mercadolivro.service.mock.MessageResponseMockFactory
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.StandardCharsets.UTF_8

@ExtendWith(MockKExtension::class)
@WebMvcTest(CustomerController::class)
class GetAllCustomerControllerTest {

    @MockkBean
    private lateinit var createCustomerUseCase: CreateCustomerUseCase

    @MockkBean
    private lateinit var getAllCustomersUseCase: GetAllCustomersUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val apiPath = "/api/v1/customers/all"

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }


    @Test
    fun `should return a pageable of customers`() {
        val domain = CustomerDomainMockFactory().buildValidPageableOfCustomerDomainFull()
        val responsePage = CustomerDomainToResponseTranslator().translate(domain)

        every { getAllCustomersUseCase.execute(any()) } returns domain

        mockMvc.perform(MockMvcRequestBuilders.get(apiPath)
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name()))
                .andExpect(status().isOk)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(responsePage))
                }

        verify(exactly = 1) { getAllCustomersUseCase.execute(any()) }
    }

    @Test
    fun `should return a message response with an internal server error status when an unknown exception happens`() {
        val errorMessage = "Internal Server Error"
        val unknownException = RuntimeException(errorMessage)
        val messageResponse = MessageResponseMockFactory().buildInternalServerErrorMessageResponse()

        every { getAllCustomersUseCase.execute(any()) } throws unknownException

        mockMvc.perform(MockMvcRequestBuilders.get(apiPath)
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name()))
                .andExpect(status().isInternalServerError)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(messageResponse))
                }

        verify(exactly = 1) { getAllCustomersUseCase.execute(any()) }
    }
}