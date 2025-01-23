package com.zogg.zoggservice.exception;

import static com.zogg.zoggservice.exception.errorcode.ErrorCode.BAD_REQUEST;
import static com.zogg.zoggservice.exception.errorcode.ErrorCode.INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zogg.zoggservice.dtos.ApiResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice(basePackages = {"com.zogg"})
@Slf4j
public class ZoggServiceGlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiResponse handleException(Exception ex) {
        String errorMsg =
                INTERNAL_SERVER_ERROR.getErrorMessage()
                        + ": "
                        + (ex.getLocalizedMessage() == null ? "Error" : ex.getLocalizedMessage());

        log.error("Exception occurred: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(INTERNAL_SERVER_ERROR.getCode())
                .errorMessage(errorMsg)
                .build();
    }

    @ExceptionHandler(ZoggServiceException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiResponse handleZoggServiceException(ZoggServiceException ex) {
        String errorMsg = ex.getLocalizedMessage() == null ? "Error" : ex.getLocalizedMessage();

        log.error("ZoggServiceException occurred: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(
                        Objects.nonNull(ex.getHttpStatus())
                                ? ex.getHttpStatus().value()
                                : BAD_REQUEST.getCode())
                .errorMessage(errorMsg)
                .build();
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ApiResponse handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JsonProcessingException occurred: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage("An error occurred while processing JSON data.")
                .build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public @ResponseBody ApiResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorMessage("API endpoint not found: " + ex.getRequestURL())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    public @ResponseBody ApiResponse handleMethodNotAllowedException(
            HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .errorMessage("Request method '" + ex.getMethod() + "' is not supported.")
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Required parameter '" + ex.getParameterName() + "' is missing.")
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiResponse handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getMessage(), ex);

        return ApiResponse.builder()
                .success(false)
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage("Validation failed for one or more fields.")
                .build();
    }
}
