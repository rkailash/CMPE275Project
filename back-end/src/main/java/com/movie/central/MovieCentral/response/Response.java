package com.movie.central.MovieCentral.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Response {

    private String message;
    private HttpStatus status;

    public Response(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }


    public HttpStatus getStatus() {
        return status;
    }
}
