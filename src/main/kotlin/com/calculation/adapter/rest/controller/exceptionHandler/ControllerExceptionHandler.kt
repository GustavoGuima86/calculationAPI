package com.calculation.adapter.rest.controller.exceptionHandler


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class ControllerExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)


    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationExceptions(
        exception: ConstraintViolationException,
        webRequest: WebRequest
    ): ResponseEntity<ApiError> {
        val errors = exception.constraintViolations.map { violation -> violation.message }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(errors))
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentValidationExceptions(
        exception: MethodArgumentNotValidException,
        webRequest: WebRequest
    ): ResponseEntity<ApiError> {
        val errors = exception.bindingResult.fieldErrors.map { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(errors))
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleOtherExceptions(exception: Exception, webRequest: WebRequest): ResponseEntity<ApiError>? {
        logger.error(exception.stackTraceToString())
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError("Server encountered an error"))
    }

    data class ApiError(val errors: List<String>) {
        constructor(error: String) : this(listOf(error))
    }
}