package com.github.hugovallada.mercadolivro.service.error

import com.github.hugovallada.mercadolivro.client.model.MessageResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException

@RestControllerAdvice
class RestErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException) : MessageResponse{
        return MessageResponse(400, "Bad Request")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(exception: IllegalArgumentException) : MessageResponse {
        return MessageResponse(400, "Bad Request")
    }

    @ExceptionHandler(AlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleAlreadyExistsException(exception: AlreadyExistsException) : MessageResponse{
        return MessageResponse(422, "Already Exists", listOf(exception.message ?:
        "There's already a customer with this email"))
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(exception: NotFoundException) : MessageResponse{
        return MessageResponse(code = 404, "Not Found", listOf(exception.message ?: "Not Found"))
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalServerError(exception: Throwable) : MessageResponse{
        return MessageResponse(500, "Internal Server Error")
    }

}