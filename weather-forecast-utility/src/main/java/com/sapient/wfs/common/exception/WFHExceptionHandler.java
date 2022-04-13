package com.sapient.wfs.common.exception;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Log
public class WFHExceptionHandler {
    @ExceptionHandler(WeatherForecastException.class)
    public ResponseEntity<WFError> handleUnauthorised(WeatherForecastException exception) {
        return ResponseEntity.status(HttpStatus.resolve(Integer.valueOf(exception.getWfError().getCode()))).body(exception.getWfError());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WFError> handleBadRequest(MissingServletRequestParameterException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<WFError> noHandlerFoundException(Exception exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<WFError> handleException(Exception exception) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<WFError> buildErrorResponse(Exception exception, HttpStatus httpStatus) {
        WFError wfError = WFError.builder().message(exception.getMessage()).code(httpStatus.toString()).build();
        return ResponseEntity.status(httpStatus).body(wfError);
    }
}
