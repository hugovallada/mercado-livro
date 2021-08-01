package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.service.error.NotFoundException
import com.github.hugovallada.mercadolivro.service.gateway.database.ExistsCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.UpdateCustomerGateway
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import io.kotest.matchers.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UpdateCustomerUseCaseTest {

    @MockK
    private lateinit var existsCustomerByEmailGateway: ExistsCustomerByEmailGateway

    @MockK
    private lateinit var updateCustomerGateway: UpdateCustomerGateway

    @InjectMockKs
    private lateinit var updateCustomerUseCase: UpdateCustomerUseCase

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should update a customer when the email exists`() {
        val domain = CustomerDomainMockFactory().buildValidCustomerDomainFull()

        every { existsCustomerByEmailGateway.execute(any()) } returns true
        every { updateCustomerGateway.execute(any()) } just runs

        updateCustomerUseCase.execute(domain)

        verify(exactly = 1) {
            existsCustomerByEmailGateway.execute(any())
            updateCustomerGateway.execute(any())
        }
    }

    @Test
    fun `should throw a not found exception when the email can't be found`() {
        val domain = CustomerDomainMockFactory().buildValidCustomerDomainFull()
        val errorMessage = "Can't find customer with email ${domain.email}"
        val notFoundException = NotFoundException(errorMessage)

        every { existsCustomerByEmailGateway.execute(any()) } returns false

        assertThrows<NotFoundException> {
            updateCustomerUseCase.execute(domain)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { existsCustomerByEmailGateway.execute(any()) }
        verify { updateCustomerGateway wasNot called }
    }

    @Test
    fun `should throw an internal server error when an unknown error happens during persist time`() {
        val domain = CustomerDomainMockFactory().buildValidCustomerDomainFull()
        val errorMessage = "Internal Server Error"
        val internalServerErrorException = RuntimeException(errorMessage)

        every { existsCustomerByEmailGateway.execute(any()) } returns true
        every { updateCustomerGateway.execute(any()) } throws internalServerErrorException

        assertThrows<RuntimeException> {
            updateCustomerUseCase.execute(domain)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) {
            existsCustomerByEmailGateway.execute(any())
            updateCustomerGateway.execute(any())
        }
    }


}