package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ExistsCustomerByEmailGatewayTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var existsCustomerByEmailGateway: ExistsCustomerByEmailGatewayImpl


    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a boolean in case the query run flawless`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()

        every { customerRepository.existsByEmail(any()) } returns true

        existsCustomerByEmailGateway.execute(customerDomain).run {
            assertTrue(this)
        }

        verify(exactly = 1) { customerRepository.existsByEmail(any()) }
    }

    @Test
    fun `should throw an exception when try to verify the existence of the email`() {
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()
        val errorMessage = "Internal Server Error"
        val internalErrorException = RuntimeException(errorMessage)

        every { customerRepository.existsByEmail(any()) } throws internalErrorException

        assertThrows<RuntimeException> {
            existsCustomerByEmailGateway.execute(customerDomain)
        }.run {
            assertEquals(errorMessage, message)
        }

        verify(exactly = 1) { customerRepository.existsByEmail(any()) }
    }

}