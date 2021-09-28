package com.example.mvc.advice

import com.example.mvc.controller.exception.ExceptionApiController
import com.example.mvc.model.http.Error
import com.example.mvc.model.http.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

// 예외 처리를 전역으로 적용(@RestController가 붙은 클래스)
// basePackageClasses - 적용할 rest controller 지정
@RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
class GlobalControllerAdvice {

    @ExceptionHandler(value = [RuntimeException::class])
    fun runtimeException(e: RuntimeException): String {
        // 상태 코드를 정해주지 않았기 때문에 200 반환
        return "Server Error"
    }

    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> {
        return ResponseEntity.internalServerError().body("Index Error")
    }

    // GET - request param
    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(
        e: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        e.constraintViolations.forEach {
            val error = Error().apply {
                field = it.propertyPath.last().name
                message = it.message
                value = it.invalidValue
            }
            errors.add(error)
        }

        val errorResponse = createErrorResponse(request, errors)

        return ResponseEntity.badRequest().body(errorResponse)
    }

    // POST
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun constraintViolationException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        e.allErrors.forEach {
            val error = Error().apply {
                field = (it as FieldError).field
                message = it.defaultMessage
                value = it.rejectedValue
            }
            errors.add(error)
        }

        val errorResponse = createErrorResponse(request, errors)

        return ResponseEntity.badRequest().body(errorResponse)
    }

    private fun createErrorResponse(
        request: HttpServletRequest,
        errorList: MutableList<Error>
    ): ErrorResponse {
        return ErrorResponse().apply {
            resultCode = "Fail"
            httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            httpMethod = request.method
            message = "요청에 에러가 발생하였습니다."
            path = request.requestURI
            timestamp = LocalDateTime.now()
            errors = errorList
        }
    }
}
