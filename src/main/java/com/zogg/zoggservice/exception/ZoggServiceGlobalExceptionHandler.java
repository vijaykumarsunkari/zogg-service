package com.zogg.zoggservice.exception;

import static com.zogg.zoggservice.exception.errorcode.ErrorCode.BAD_REQUEST;
import static com.zogg.zoggservice.exception.errorcode.ErrorCode.INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zogg.zoggservice.dtos.ApiResponse;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackages = {"com.zogg"})
@Slf4j
public class ZoggServiceGlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ApiResponse handleException(Exception ex) {
    String errorMsg =
        INTERNAL_SERVER_ERROR.getErrorMessage() + ":" + (ex.getLocalizedMessage() == null ? "Error"
            : ex.getLocalizedMessage());
    ApiResponse apiOutput = new ApiResponse();
    apiOutput.setSuccess(false);
    apiOutput.setErrorCode(INTERNAL_SERVER_ERROR.getCode());
    apiOutput.setErrorMessage(errorMsg);
    log.error("Exception occurred : {} {}", ex, ex.getStackTrace(), ex);
    return apiOutput;
  }

  @ExceptionHandler(ZoggServiceException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiResponse handleException(ZoggServiceException ex) {
    String errorMsg = ex.getLocalizedMessage() == null ? "Error" : ex.getLocalizedMessage();
    ApiResponse apiOutput = new ApiResponse();
    apiOutput.setSuccess(false);
    apiOutput.setErrorCode(
        Objects.nonNull(ex.getHttpStatus()) ? ex.getHttpStatus().value() : BAD_REQUEST.getCode());
    apiOutput.setErrorMessage(errorMsg);
    log.error("Exception occurred : {} {}", ex, ex.getStackTrace(), ex);
    return apiOutput;
  }

  @ExceptionHandler(JsonProcessingException.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ApiResponse handleJsonProcessingException(JsonProcessingException ex) {
    String errorMsg = "An error occurred while processing JSON data.";
    log.error("Exception occurred: {}", ex.getMessage(), ex);
    ApiResponse apiOutput = new ApiResponse();
    apiOutput.setSuccess(false);
    apiOutput.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    apiOutput.setErrorMessage(errorMsg);
    return apiOutput;
  }
}
