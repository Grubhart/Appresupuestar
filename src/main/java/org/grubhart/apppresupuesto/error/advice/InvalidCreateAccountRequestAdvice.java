package org.grubhart.apppresupuesto.error.advice;

import org.grubhart.apppresupuesto.error.exception.InvalidCreateAccountRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidCreateAccountRequestAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidCreateAccountRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidCreateAccountRequestHandler(InvalidCreateAccountRequestException ex){
        return ex.getMessage();
    }
}
