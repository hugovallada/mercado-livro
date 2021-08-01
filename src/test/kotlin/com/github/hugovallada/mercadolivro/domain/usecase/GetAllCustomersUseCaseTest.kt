package com.github.hugovallada.mercadolivro.domain.usecase

import com.github.hugovallada.mercadolivro.service.gateway.database.GetAllCustomersGateway
import com.github.hugovallada.mercadolivro.service.gateway.database.impl.GetAllCustomersGatewayImpl
import com.github.hugovallada.mercadolivro.service.gateway.database.translator.CustomerDBToDomainTranslator
import com.github.hugovallada.mercadolivro.service.mock.CustomerDBMockFactory
import com.github.hugovallada.mercadolivro.service.mock.CustomerDomainMockFactory
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

@ExtendWith(MockKExtension::class)
class GetAllCustomersUseCaseTest{

    @MockK
    private lateinit var getAllCustomersGateway: GetAllCustomersGatewayImpl

    @InjectMockKs
    private lateinit var getAllCustomersUseCase: GetAllCustomersUseCase

    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should return a page of customer domains`(){
        val pageOfDB = CustomerDBMockFactory().buildAPageableOfCustomerDB()
        val pageOfDomains = CustomerDBToDomainTranslator().translate(pageOfDB)

        every { getAllCustomersGateway.execute(any()) } returns pageOfDomains

        getAllCustomersUseCase.execute(PageRequest.of(5, 5)).run {
            shouldBe(pageOfDomains)
        }

        verify(exactly = 1) { getAllCustomersGateway.execute(any()) }
    }

    @Test
    fun `should throw an exception when some unknown error happens during the query`(){
        val runtimeException = RuntimeException("Internal Server Error")

        every { getAllCustomersGateway.execute(any()) } throws runtimeException

        assertThrows<RuntimeException> {
            getAllCustomersUseCase.execute(PageRequest.of(5,5))
        }.run {
            message.shouldBe(runtimeException.message)
        }

        verify(exactly = 1) { getAllCustomersGateway.execute(any()) }
    }
}