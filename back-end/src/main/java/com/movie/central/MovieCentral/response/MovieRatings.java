package com.movie.central.MovieCentral.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;



@Data
@Getter
@Setter
public class MovieRatings {
    private BigInteger id;
    private String name;
    private BigDecimal rating;
}