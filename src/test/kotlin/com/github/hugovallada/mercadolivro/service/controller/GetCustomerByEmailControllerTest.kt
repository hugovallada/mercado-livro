package com.github.hugovallada.mercadolivro.service.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hugovallada.mercadolivro.domain.translator.CustomerDomainToResponseTranslator
import com.github.hugovallada.mercadolivro.domain.usecase.CreateCustomerUseCase
import com.github.hugovallada.mercadolivro.domain.usecase.GetAllCustomersUseCase
import com.github.hugovallada.mercadolivro.domain.usecase.GetCustomerByEmailUseCase
import com.github.hugovallada.mercadolivro.service.error.NotFoundException
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.charset.StandardCharsets.UTF_8

@ExtendWith(MockKExtension::class)
@WebMvcTest(CustomerController::class)
class GetCustomerByEmailControllerTest {

    @MockkBean
    private lateinit var createCustomerUseCase: CreateCustomerUseCase

    @MockkBean
    private lateinit var getAllCustomersUseCase: GetAllCustomersUseCase

    @MockkBean
    private lateinit var getCustomerByEmailUseCase: GetCustomerByEmailUseCase

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    private val apiPath = "/api/v1/customers/{email}"

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a customer response with a ok status`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomainFull()
        val customerResponse = CustomerDomainToResponseTranslator().translate(customerDomain)

        every { getCustomerByEmailUseCase.execute(any()) } returns customerDomain

        mockMvc.perform(get(apiPath, "email@email.com")
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name()))
                .andExpect(status().isOk)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(customerResponse))
                }

        verify(exactly = 1) { getCustomerByEmailUseCase.execute(any()) }
    }

    @Test
    fun `should return a message response with not found status when the email can't be found`() {
        val errorMessage = "Can't find a customer with this email"
        val notFoundException = NotFoundException(errorMessage)
        val messageResponse = MessageResponseMockFactory().buildNotFoundMessageResponse(errorMessage)


        every { getCustomerByEmailUseCase.execute(any()) } throws notFoundException

        mockMvc.perform(get(apiPath, "email@email.com")
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name()))
                .andExpect(status().isNotFound)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(messageResponse))
                }

        verify(exactly = 1) { getCustomerByEmailUseCase.execute(any()) }
    }

    @Test
    fun `should return a message response with an internal server error status when an unknown exception happens`() {
        val messageResponse = MessageResponseMockFactory().buildInternalServerErrorMessageResponse()

        every { getCustomerByEmailUseCase.execute(any()) } throws RuntimeException("internal server error")

        mockMvc.perform(get(apiPath, "email@email.com")
                .contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(UTF_8.name()))
                .andExpect(status().isInternalServerError)
                .andDo(print())
                .andReturn().run {
                    response.contentAsString.shouldBe(mapper.writeValueAsString(messageResponse))
                }

        verify(exactly = 1) { getCustomerByEmailUseCase.execute(any()) }
    }
}