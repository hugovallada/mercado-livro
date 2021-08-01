package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
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
class UpdateCusomerGatewayTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var updateCustomerGateway: UpdateCustomerGatewayImpl

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should update the customer`(){
        val domain = CustomerDomainMockFactory().buildValidCustomerDomainFull()

        every { customerRepository.updateName(any(), any()) } just runs

        updateCustomerGateway.execute(domain)

        verify(exactly = 1) { customerRepository.updateName(any(), any()) }
    }

    @Test
    fun `should throw an internal error exception when an unknown error happens`(){
        val errorMessage = "Internal Server Error"
        val unknownException = RuntimeException(errorMessage)
        val domain = CustomerDomainMockFactory().buildValidCustomerDomainFull()

        every { customerRepository.updateName(any(), any()) } throws unknownException

        assertThrows<RuntimeException> {
            updateCustomerGateway.execute(domain)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { customerRepository.updateName(any(), any()) }
    }


}