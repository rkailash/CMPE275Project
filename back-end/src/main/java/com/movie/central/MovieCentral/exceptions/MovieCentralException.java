package com.movie.central.MovieCentral.exceptions;

public class MovieCentralException extends Exception{

    Error error;

    public MovieCentralException(Error error){
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error.getDescription();
    }

    public Error getError() {
        return error;
    }
}
