package com.github.hugovallada.mercadolivro.service.gateway.database.impl

import com.github.hugovallada.mercadolivro.service.error.NotFoundException
import com.github.hugovallada.mercadolivro.service.gateway.database.repository.CustomerRepository
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import com.github.hugovallada.mercadolivro.service.mock.CustomerDBMockFactory
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
class GetCustomerbyEmailGatewayTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    private lateinit var getCustomerByEmailGateway: GetCustomerByEmailGatewayImpl


    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a customer domain when the email exists in the database`(){
        val customerDB = CustomerDBMockFactory().buildValidCustomerDB()
        val customerDomain = CustomerDBToDomainTranslator().translate(customerDB)

        every { customerRepository.findByEmail(any()) } returns customerDB

        getCustomerByEmailGateway.execute("email@email.com").run {
            shouldBe(customerDomain)
        }

        verify(exactly = 1) { customerRepository.findByEmail(any()) }
    }

    @Test
    fun `should throw a not found exception when the email doesn't exists`(){
        val email = "email@email.com"
        val errorMessage = "Can't find customer with email $email"


        every { customerRepository.findByEmail(any()) } returns null

        assertThrows<NotFoundException>{
            getCustomerByEmailGateway.execute(email)
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { customerRepository.findByEmail(any()) }
    }

    @Test
    fun `should throw an internal server error when an unknown exception happens`(){
        val errorMessage = "Internal Server Error"
        val unknownException = RuntimeException(errorMessage)

        every { customerRepository.findByEmail(any()) } throws unknownException

        assertThrows<java.lang.RuntimeException> {
            getCustomerByEmailGateway.execute("email@email.com")
        }.run {
            message.shouldBe(errorMessage)
        }

        verify(exactly = 1) { customerRepository.findByEmail(any()) }
    }
}