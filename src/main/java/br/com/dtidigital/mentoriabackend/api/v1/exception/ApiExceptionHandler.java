package br.com.dtidigital.mentoriabackend.api.v1.exception;

import br.com.dtidigital.mentoriabackend.api.v1.exception.Error.ErrorBuilder;
import br.com.dtidigital.mentoriabackend.core.properties.Messages;
import br.com.dtidigital.mentoriabackend.domain.exception.BadRequestException;
import br.com.dtidigital.mentoriabackend.domain.exception.BusinessException;
import br.com.dtidigital.mentoriabackend.domain.exception.EntityAlreadyInUseException;
import br.com.dtidigital.mentoriabackend.domain.exception.InternalServiceException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static br.com.dtidigital.mentoriabackend.api.v1.exception.ErrorType.INTERNAL_SERVER_ERROR;
import static java.lang.String.format;

/**
 * Global API Exception Handler.
 * Responsible for handling all exceptions thrown by the application and converting them into appropriate HTTP responses.
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final Messages messages;

    private static final String MSG_GENERIC_END_USER = "message.generic.end.user";
    private static final String MSG_INVALID_PARAMETER = "invalid.parameter";

    /**
     * Handles resource not found exceptions (404).
     *
     * @param ex      original exception
     * @param headers HTTP headers
     * @param status  HTTP status code
     * @param request web request
     * @return ResponseEntity containing error details
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
        NoHandlerFoundException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorType errorType = ErrorType.RESOURCE_NOT_FOUND;
        String detail = String.format("The resource '%s' you tried to access does not exist", ex.getRequestURL());

        Error error = createErrorBuilder(httpStatus, errorType, detail)
            .userMessage("Resource not found")
            .build();

        return handleExceptionInternal(ex, error, headers, httpStatus, request);
    }

    /**
     * Handles method argument validation exceptions.
     *
     * @param ex      validation exception
     * @param headers HTTP headers
     * @param status  HTTP status code
     * @param request web request
     * @return ResponseEntity containing validation error details
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {

        ErrorType errorType = ErrorType.INVALID_DATA;
        String detail = "One or more fields have invalid values.";

        List<Error.FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> Error.FieldError.builder()
                .name(fieldError.getField())
                .userMessage(fieldError.getDefaultMessage())
                .build())
            .toList();

        Error error = createErrorBuilder(HttpStatus.BAD_REQUEST, errorType, detail)
            .userMessage(detail)
            .fieldErrorList(fieldErrors)
            .build();

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles entity not found exceptions.
     *
     * @param ex      The entity not found exception.
     * @param request The web request.
     * @return ResponseEntity with status 204 (No Content) and error details.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NO_CONTENT;
        return handleExceptionInternal(ex, null, new HttpHeaders(), status, request);
    }

    /**
     * Handles bad request exceptions.
     *
     * @param ex      exception to bad request
     * @param request web request
     * @return ResponseEntity with status 400 and error details
     */
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<Object> handleBadRequest(

        BadRequestException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorType errorType = ErrorType.BAD_REQUEST;
        String detail = ex.getMessage();
        Error error = createErrorBuilder(status, errorType, detail)
            .userMessage(detail)
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handles business exceptions.
     *
     * @param ex      exception to business
     * @param request web request
     * @return ResponseEntity with status 500 and error details
     */
    @ExceptionHandler(BusinessException.class)
    ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorType errorType = ErrorType.BUSINESS_EXCEPTION;
        String detail = ex.getMessage();
        Error error = createErrorBuilder(status, errorType, detail)
            .userMessage(detail)
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handles entity not found exceptions.
     *
     * @param ex      entity not found exception
     * @param request web request
     * @return ResponseEntity with 404 status and error details
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {

        ErrorType errorType = ErrorType.INVALID_DATA;
        String detail = "The request body is invalid. Check syntax error.";

        log.error("Invalid message: ", ex);

        Error error = createErrorBuilder(HttpStatus.BAD_REQUEST, errorType, detail)
            .userMessage("The request body is invalid. Check JSON syntax.")
            .build();

        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles access denied exceptions.
     *
     * @param ex      access denied exception
     * @param request web request
     * @return ResponseEntity with 403 status and error details
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorType errorType = ErrorType.ACCESS_DENIED;
        String detail = ex.getMessage();
        Error error = createErrorBuilder(status, errorType, detail)
            .userMessage(detail)
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handles internal service exceptions.
     *
     * @param ex      internal service exception
     * @param request web request
     * @return ResponseEntity containing error details and a 500 status code
     */
    @ExceptionHandler(InternalServiceException.class)
    public ResponseEntity<Object> handleInternalServiceException(
        InternalServiceException ex,
        WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = String.valueOf(ex.getCause());

        log.error("Internal server error: ", ex);
        Error error = createErrorBuilder(status, INTERNAL_SERVER_ERROR, detail)
            .userMessage(ex.getMessage())
            .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handle with database exception.
     *
     * @param ex      exception from database
     * @param request web request
     * @return ResponseEntity with status 500 and error details
     */
    @ExceptionHandler(SQLException.class)
    ResponseEntity<Object> handleOracleDatabaseException(SQLException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = ex.getMessage();
        Error error = createErrorBuilder(status, INTERNAL_SERVER_ERROR, detail)
            .userMessage(detail)
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handle with entity already in use exception.
     *
     * @param ex     exception to entity already in use
     * @param request web request
     * @return ResponseEntity with status 409 and error details
     */
    @ExceptionHandler(EntityAlreadyInUseException.class)
    ResponseEntity<Object> handleEntityAlreadyInUseException(EntityAlreadyInUseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorType errorType = ErrorType.ENTITY_ALREADY_IN_USE;
        String detail = ex.getMessage();
        Error error = createErrorBuilder(status, errorType, detail)
            .userMessage(detail)
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handle with NullPointerExceptions
     *
     * @param ex      exception to nullpointer
     * @param request web request
     * @return ResponseEntity with status 500 and error details
     */
    @ExceptionHandler(NullPointerException.class)
    ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "Ocorreu um erro interno no servidor.";

        log.error("NullPointerException: ", ex);

        Error error = createErrorBuilder(status, INTERNAL_SERVER_ERROR, detail)
            .userMessage(INTERNAL_SERVER_ERROR.getTitle())
            .detail(ex.getMessage())
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * handle with RestClientException
     *
     * @param ex      exception to rest client
     * @param request web request
     * @return ResponseEntity with status 500 and error details
     */
    @ExceptionHandler(RestClientException.class)
    ResponseEntity<Object> handleRestClientException(RestClientException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "An error occurred during communication with external services.";

        log.error("RestClientException: ", ex);

        Error error = createErrorBuilder(status, INTERNAL_SERVER_ERROR, detail)
            .userMessage(INTERNAL_SERVER_ERROR.getTitle())
            .detail(ex.getMessage())
            .build();
        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    /**
     * Handles internal exceptions.
     *
     * @param ex      The original exception.
     * @param body    The response body.
     * @param headers The HTTP headers.
     * @param status  The HTTP status code.
     * @param request The web request.
     * @return A standardized ResponseEntity containing error details.
     */
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorBuilder errorBuilder = Error.builder()
            .timestamp(OffsetDateTime.now())
            .status(status.value())
            .userMessage(messages.getMessage(MSG_GENERIC_END_USER));

        if (body == null) {
            body =  errorBuilder.title(status.getReasonPhrase()).build();
        } else if (body instanceof String string) {
            body = errorBuilder.title(string).build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * Handles type mismatch exceptions.
     *
     * @param ex      type mismatch exception
     * @param headers HTTP headers
     * @param status  HTTP status code
     * @param request web request
     * @return ResponseEntity with error details
     */
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                methodArgumentTypeMismatchException, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }


    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest request) {

        ErrorType errorType = ErrorType.INVALID_PARAMETER;

        String detail = format(messages.getMessage(MSG_INVALID_PARAMETER),
            ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        Error error = createErrorBuilder(status, errorType, detail)
            .userMessage(messages.getMessage(MSG_GENERIC_END_USER))
            .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    /**
     * Handles binding exceptions.
     *
     * @param ex      binding exception
     * @param headers HTTP headers
     * @param status  HTTP status code
     * @param request web request
     * @return ResponseEntity with validation error details
     */
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }


    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request, BindingResult bindingResult) {
        ErrorType errorType = ErrorType.INVALID_DATA;
        String detail = "One or more fields are invalid. Please fill in correctly and try again.";

        List<Error.FieldError> fieldErrorList = bindingResult.getFieldErrors().stream()
            .map(fieldError -> {
                String message = messages.getMessage(fieldError);
                return Error.FieldError.builder()
                    .name(fieldError.getField())
                    .userMessage(message)
                    .build();
            })
            .toList();

        Error error = createErrorBuilder(status, errorType, detail)
            .userMessage(detail)
            .fieldErrorList(fieldErrorList)
            .build();

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    /**
     * Creates an Error builder with common values filled in.
     *
     * @param status    HTTP status
     * @param errorType error type
     * @param detail    error details
     * @return ErrorBuilder configured with basic values
     */
    private Error.ErrorBuilder createErrorBuilder(HttpStatus status, ErrorType errorType, String detail) {
        return Error.builder()
            .timestamp(OffsetDateTime.now())
            .status(status.value())
            .title(errorType.getTitle())
            .detail(detail);
    }

    /**
     * Handles exceptions not caught by other handlers.
     * Acts as a "catch-all" for any exception not handled by a more specific handler.
     * Returns a 500 error (Internal Server Error) with a generic message.
     *
     * @param ex      The uncaught exception.
     * @param request The web request that caused the exception.
     * @return A {@link ResponseEntity} containing an {@link Error} object with error details and 500 status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "An internal server error occurred.";

        log.error("Generic error - Uncaught exception: ", ex);

        Error error = createErrorBuilder(status, INTERNAL_SERVER_ERROR, detail)
            .userMessage(INTERNAL_SERVER_ERROR.getTitle())
            .build();

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

}
