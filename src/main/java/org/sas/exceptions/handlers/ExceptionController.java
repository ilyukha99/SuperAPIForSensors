package org.sas.exceptions.handlers;

import org.sas.responses.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice(annotations = CustomExceptionHandler.class)
public class ExceptionController {
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<HttpResponse> handleException(InvalidFormatException exception) {
        return new ResponseEntity<>(new HttpResponse(3, "incorrect data format"), HttpStatus.BAD_REQUEST);
    }
}
