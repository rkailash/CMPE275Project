package com.movie.central.MovieCentral.exceptions;

import com.movie.central.MovieCentral.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.ParseException;

@ControllerAdvice(basePackages = {"com.movie.central.MovieCentral"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MovieCentralException.class})
    public org.springframework.http.ResponseEntity handleConflict(MovieCentralException ex) {
        Response errorDetails = new Response(ex.getError().getDescription(), ex.getError().getCode());
        System.out.println(ex.getMessage());
        return new org.springframework.http.ResponseEntity(errorDetails, errorDetails.getStatus());
    }


    @ExceptionHandler(value = {Exception.class})
    public final org.springframework.http.ResponseEntity handleAllExceptions(Exception ex, WebRequest request) {
        Response errorDetails = new Response(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        ex.printStackTrace();
        return new org.springframework.http.ResponseEntity(errorDetails, errorDetails.getStatus());
    }


}
