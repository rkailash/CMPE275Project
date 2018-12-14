package com.movie.central.MovieCentral.response;

import com.movie.central.MovieCentral.enums.Genre;
import com.movie.central.MovieCentral.model.Actor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Data
@Getter
@Setter
public class PopularMovieDetails {
    private BigInteger id;
    private BigInteger playCount;
    private String name;
    private Double averageRating;
    private String director;
    private Genre genre;
    private List<String> actors;
}
