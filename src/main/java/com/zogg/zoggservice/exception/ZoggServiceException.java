package com.zogg.zoggservice.exception;

import com.zogg.zoggservice.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ZoggServiceException extends RuntimeException {

    private Integer errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ZoggServiceException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getErrorMessage();
    }

    public ZoggServiceException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorMessage;
    }

    public ZoggServiceException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ErrorCode.BAD_REQUEST.getCode();
        this.errorMessage = errorMessage;
    }

    public ZoggServiceException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorCode = ErrorCode.BAD_REQUEST.getCode();
        this.errorMessage = errorMessage;
    }

    public ZoggServiceException(String errorMessage, Exception e) {
        super(e);
        this.errorCode = ErrorCode.BAD_REQUEST.getCode();
        this.errorMessage = errorMessage;
    }

    public ZoggServiceException(Integer customErrorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = customErrorCode;
        this.errorMessage = errorMessage;
    }

    public ZoggServiceException(Exception e, String errorMessage) {
        super(e);
        this.errorMessage = errorMessage;
    }

    public ZoggServiceException(Exception e) {
        super(e);
        this.errorMessage = e.getMessage();
    }
}
