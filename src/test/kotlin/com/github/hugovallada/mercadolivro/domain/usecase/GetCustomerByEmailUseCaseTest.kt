package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.service.error.NotFoundException
import com.github.hugovallada.mercadolivro.service.gateway.database.impl.GetCustomerByEmailGatewayImpl
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetCustomerByEmailUseCaseTest {

    @MockK
    private lateinit var getCustomerByEmailGateway: GetCustomerByEmailGatewayImpl

    @InjectMockKs
    private lateinit var getCustomerByEmailUseCase: GetCustomerByEmailUseCase


    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a customer domain if it can be retrieve from the database`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomainFull()

        every { getCustomerByEmailGateway.execute(any()) } returns customerDomain

        getCustomerByEmailUseCase.execute("email@email.com").run{
            shouldBe(customerDomain)
        }

        verify(exactly = 1) { getCustomerByEmailGateway.execute(any()) }
    }

    @Test
    fun `should throw an unknown exception when the email doesn't exists in the database`(){
        val email = "email@email.com"
        val errorMessage = "Can't find customer with email $email"
        val notFoundException = NotFoundException(errorMessage)

        every { getCustomerByEmailGateway.execute(any()) } throws notFoundException

        assertThrows<NotFoundException> {
            getCustomerByEmailUseCase.execute(email)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { getCustomerByEmailGateway.execute(any()) }
    }

    @Test
    fun `should throw an internal server error when an unknown exceptions happens in the gateway`(){
        val errorMessage = "Internal Server Error"
        val unknownException = RuntimeException(errorMessage)

        every { getCustomerByEmailGateway.execute(any()) } throws unknownException

        assertThrows<RuntimeException> {
            getCustomerByEmailUseCase.execute("email@email.com")
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { getCustomerByEmailGateway.execute(any()) }
    }
}