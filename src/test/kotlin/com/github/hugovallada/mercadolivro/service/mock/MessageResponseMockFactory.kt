package com.github.hugovallada.mercadolivro.service.mock

import com.github.hugovallada.mercadolivro.client.model.MessageResponse

class MessageResponseMockFactory {

    fun buildBadRequestMessageResponse() = MessageResponse(
            code = 400, message = "Bad Request"
    )

    fun buildAlreadyExistsMessageResponse(error: String) = MessageResponse(
            code = 422, message = "Already Exists",
            errors = listOf(error)
    )

    fun buildInternalServerErrorMessageResponse() = MessageResponse(
            code = 500, message = "Internal Server Error"
    )

    fun buildNotFoundMessageResponse(error: String) = MessageResponse(
            code = 404, message = "Not Found",
            errors = listOf(error)
    )

}