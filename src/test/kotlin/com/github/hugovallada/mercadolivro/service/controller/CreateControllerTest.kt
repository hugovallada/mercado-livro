package com.github.hugovallada.mercadolivro.service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hugovallada.mercadolivro.domain.usecase.CreateCustomerUseCase
import com.github.hugovallada.mercadolivro.domain.usecase.GetAllCustomersUseCase
import com.github.hugovallada.mercadolivro.service.error.AlreadyExistsException
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import com.github.hugovallada.mercadolivro.service.mock.CustomerRequestMockFactory
import com.github.hugovallada.mercadolivro.service.mock.MessageResponseMockFactory
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.called
import io.mockk.clearAllMocks
import io.mockk.every
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

    @MockkBean
    private lateinit var getAllCustomersUseCase: GetAllCustomersUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val apiPath = "/api/v1/customers"

    @BeforeEach
    fun setUp() {
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
                    response.contentAsString.shouldBe(mapper.writeValueAsString(customerDomain))
                }

        verify(exactly = 1) { createCustomerUseCase.execute(any()) }
    }

    @Test
    fun `should return a message response with a bad request status when they payload is invalid`() {
        val messageResponse = MessageResponseMockFactory().buildBadRequestMessageResponse()
        val invalidRequest = CustomerRequestMockFactory().buildInvalidCustomerRequest()

        mockMvc.perform(post(apiPath)
                .content(mapper.writeValueAsString(invalidRequest))
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name()))
                .andExpect(status().isBadRequest)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(messageResponse))
                }

        verify { createCustomerUseCase wasNot called }
    }

    @Test
    fun `should return a message response with a unprocessable entity status when the email has already been used`() {
        val request = CustomerRequestMockFactory().buildValidCustomerRequest()
        val errorMessage = "There's already a customer with this email"
        val alreadyExistsException = AlreadyExistsException(errorMessage)
        val messageResponse = MessageResponseMockFactory().buildAlreadyExistsMessageResponse(errorMessage)

        every { createCustomerUseCase.execute(any()) } throws alreadyExistsException

        mockMvc.perform(post(apiPath).content(mapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON_VALUE).characterEncoding(UTF_8.name()))
                .andExpect(status().isUnprocessableEntity)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(messageResponse))
                }

        verify(exactly = 1) { createCustomerUseCase.execute(any()) }
    }

    @Test
    fun `should return a message response with an internal server error status when an unknown error happens`() {
        val errorMessage = "Internal Server Error"
        val request = CustomerRequestMockFactory().buildValidCustomerRequest()
        val messageResponse = MessageResponseMockFactory().buildInternalServerErrorMessageResponse()
        val runtimeException = RuntimeException(errorMessage)

        every { createCustomerUseCase.execute(any()) } throws runtimeException

        mockMvc.perform(post(apiPath)
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name())
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(messageResponse))
                }

        verify(exactly = 1) { createCustomerUseCase.execute(any()) }
    }
}