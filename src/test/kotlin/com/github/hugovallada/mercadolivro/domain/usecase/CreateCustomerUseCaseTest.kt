package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.service.error.AlreadyExistsException
import com.github.hugovallada.mercadolivro.service.gateway.database.ExistsCustomerByEmailGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.PersistCustomerGateway
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import io.kotest.matchers.shouldBe
import io.mockk.called
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
class CreateCustomerUseCaseTest {

    @MockK
    private lateinit var existsCustomerByEmailGateway: ExistsCustomerByEmailGateway

    @MockK
    private lateinit var persistCustomerGateway: PersistCustomerGateway

    @InjectMockKs
    private lateinit var createCustomerUseCase: CreateCustomerUseCase

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a new created share domain`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()
        val customerDomainFull = CustomerDomainMockFactory().buildValidCustomerDomainFull()

        every { existsCustomerByEmailGateway.execute(any()) } returns false
        every { persistCustomerGateway.execute(any()) } returns customerDomainFull

        createCustomerUseCase.execute(customerDomain).run {
            this.shouldBe(customerDomainFull)
        }

        verify(exactly = 1) {
            existsCustomerByEmailGateway.execute(any())
            persistCustomerGateway.execute(any())
        }
    }

    @Test
    fun `should throw an already exists exception when the email already exists in the database`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()
        val errorMessage = "There's already a customer with this email"

        every { existsCustomerByEmailGateway.execute(any()) } returns true

        assertThrows<AlreadyExistsException> {
            createCustomerUseCase.execute(customerDomain)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { existsCustomerByEmailGateway.execute(any()) }
        verify { persistCustomerGateway wasNot called }
    }

    @Test
    fun `should throw an internal exception when there's an error during the search query`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()
        val errorMessage = "Internal Server Error"
        val internalErrorException = RuntimeException(errorMessage)

        every { existsCustomerByEmailGateway.execute(any()) } throws internalErrorException

        assertThrows<java.lang.RuntimeException> {
            createCustomerUseCase.execute(customerDomain)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { existsCustomerByEmailGateway.execute(any()) }
        verify { persistCustomerGateway wasNot called }
    }

    @Test
    fun `should throw an internal exception when there's an error during the persist query`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()
        val errorMessage = "Internal Server Error"
        val internalErrorException = RuntimeException(errorMessage)

        every { existsCustomerByEmailGateway.execute(any()) } returns false
        every { persistCustomerGateway.execute(any()) } throws internalErrorException

        assertThrows<java.lang.RuntimeException> {
            createCustomerUseCase.execute(customerDomain)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) {
            existsCustomerByEmailGateway.execute(any())
            persistCustomerGateway.execute(any())
        }

    }
}