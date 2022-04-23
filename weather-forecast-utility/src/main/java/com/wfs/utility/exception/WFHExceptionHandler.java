package com.wfs.utility.exception;

import com.wfs.utility.constants.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
@Slf4j
public class WFHExceptionHandler {
    private static final String LOG_CONSTANT = "{} {}";

    @ExceptionHandler(WeatherForecastException.class)
    public ResponseEntity<WFError> handleWeatherForecastException(WeatherForecastException exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        if (null != exception.getCode() && null != HttpStatus.resolve(exception.getCode())) {
            return buildErrorResponse(exception, HttpStatus.resolve(exception.getCode()));
        } else {
            return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<WFError> handleAccessDeniedException(AccessDeniedException exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        return buildErrorResponse(MessageConstants.ACCESS_DENIED_MESSAGE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(WeatherForecastServiceException.class)
    public ResponseEntity<WFError> handleWeatherForecastServiceException(WeatherForecastServiceException exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        if (null != exception.getCode() && null != HttpStatus.resolve(exception.getCode())) {
            return buildErrorResponse(exception, HttpStatus.resolve(exception.getCode()));
        } else {
            return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<WFError> handleBadRequest(MissingServletRequestParameterException exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<WFError> noHandlerFoundException(Exception exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<WFError> noHandlerFoundException(HttpMediaTypeNotSupportedException exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<WFError> handleException(Exception exception) {
        log.error(LOG_CONSTANT, exception.getMessage(), exception);
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<WFError> buildErrorResponse(Exception exception, HttpStatus httpStatus) {
        WFError wfError = WFError.builder().message(exception.getMessage()).code(httpStatus.value()).build();
        return ResponseEntity.status(httpStatus).body(wfError);
    }

    private ResponseEntity<WFError> buildErrorResponse(String message, HttpStatus httpStatus) {
        WFError wfError = WFError.builder().message(message).code(httpStatus.value()).build();
        return ResponseEntity.status(httpStatus).body(wfError);
    }
}
