package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import com.github.hugovallada.mercadolivro.service.mock.CustomerDBMockFactory
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PersistCustomerGatewayTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var persistCustomerGateway: PersistCustomerGatewayImpl

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a new domain`() {
        val entryCustomerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()
        val customerDB = CustomerDBMockFactory().buildValidCustomerDB()
        val customerDomain = CustomerDBToDomainTranslator().translate(customerDB)

        every { customerRepository.save(any()) } returns customerDB

        persistCustomerGateway.execute(entryCustomerDomain).run {
            assertEquals(customerDomain, this)
        }

        verify(exactly = 1) { customerRepository.save(any()) }
    }

    @Test
    fun `should throw an error when the customer can't be saved`() {
        val errorMessage = "Internal Error"
        val internalErrorException = RuntimeException(errorMessage)
        val customerDomain = CustomerDomainMockFactory().buildValidCustomerDomain()

        every { customerRepository.save(any()) } throws internalErrorException

        assertThrows<RuntimeException> {
            persistCustomerGateway.execute(customerDomain)
        }.run {
            assertEquals(internalErrorException.message, message)
        }

        verify(exactly = 1) { customerRepository.save(any()) }
    }

}