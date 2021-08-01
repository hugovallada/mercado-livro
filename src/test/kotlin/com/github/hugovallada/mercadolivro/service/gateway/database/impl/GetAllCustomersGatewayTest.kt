package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import com.github.hugovallada.mercadolivro.service.mock.CustomerDBMockFactory
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
class GetAllCustomersGatewayTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var getAllCustomersGateway: GetAllCustomersGatewayImpl

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a page of domains`() {
        val pageable = CustomerDBMockFactory().buildAPageableOfCustomerDB()
        val pageableDomains = CustomerDBToDomainTranslator().translate(pageable)


        every { customerRepository.getAll(any()) } returns pageable

        getAllCustomersGateway.execute(PageRequest.of(1, 5)).run {
            this.shouldNotBe(null)
            this.shouldBe(pageableDomains)
        }

        verify(exactly = 1) { customerRepository.getAll(any()) }
    }

    @Test
    fun `should throw an exception when trying to retrieve a page of customers`() {
        val runtimeException = RuntimeException("Internal Server Error")

        every { customerRepository.getAll(any()) } throws runtimeException

        assertThrows<RuntimeException> {
            getAllCustomersGateway.execute(PageRequest.of(5, 5))
        }.run {
            message.shouldBe(runtimeException.message)
        }

        verify(exactly = 1) { customerRepository.getAll(any()) }
    }
}

