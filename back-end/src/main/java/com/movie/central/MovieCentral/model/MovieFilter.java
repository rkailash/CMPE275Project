package com.movie.central.MovieCentral.model;

import com.movie.central.MovieCentral.enums.Genre;
import com.movie.central.MovieCentral.enums.MpaaRating;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MovieFilter implements Serializable{

    List<Genre> genres;

    Integer releaseYear;

    String actor;

    String director;

    List<MpaaRating> mpaaRatings;

    Double averageRating;

    String keywords;


}
