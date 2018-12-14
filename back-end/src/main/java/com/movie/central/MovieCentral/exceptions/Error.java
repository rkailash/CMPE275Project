package com.movie.central.MovieCentral.exceptions;

import org.springframework.http.HttpStatus;

public enum Error {

    DUPLICATE_USER(HttpStatus.CONFLICT, "User with this email already exists, please use another email"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not registered, Please register to continue"),
    INVALID_SUBSCRIPTION_MONTHS(HttpStatus.BAD_REQUEST, "Please select atleast one month for subscription"),
    INVALID_DATE_EXCEPTION(HttpStatus.BAD_REQUEST, "Please specify valid date"),
    DUPLICATE_MOVIE(HttpStatus.CONFLICT, "Movie already exists"),
    INVALID_CUSTOMER_OR_MOVIE(HttpStatus.BAD_REQUEST, "Either user or the movie does not exist."),
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "Movie not found, please select another movie"),
    MOVIE_NEEDS_SUBSCRIPTION(HttpStatus.FORBIDDEN, "Movie needs subscription, please subscribe to view the movie"),
    MOVIE_NEEDS_PAYPERVIEW(HttpStatus.FORBIDDEN, "Movie needs pay per view, please pay to view the movie");

    private final HttpStatus code;
    private final String description;

    Error(HttpStatus code, String description) {
        this.code = code;
        this.description = description;
    }

    public HttpStatus getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + " : " + description;
    }


}
