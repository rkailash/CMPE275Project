package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.enums.Genre;
import com.movie.central.MovieCentral.enums.MpaaRating;
import com.movie.central.MovieCentral.model.CustomerRatingId;
import com.movie.central.MovieCentral.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long>{

    public Movie findByTitleAndReleaseYear(String title, Integer releaseYear);

    public Movie findMovieById(Long id);

    List<Movie> findDistinctByTitleContainingOrSynopsysContainingOrGenreContainingOrCountryContainingOrReleaseYearContainingOrStudioContainingAllIgnoreCase(String title, String synopsys, String genre, String country, String releaseYear, String studio);


    default List<Movie> findMoviesByAttributes(String title, String synopsys, String genre, String country, String releaseYear, String studio){
        return findDistinctByTitleContainingOrSynopsysContainingOrGenreContainingOrCountryContainingOrReleaseYearContainingOrStudioContainingAllIgnoreCase(title, synopsys, genre, country, releaseYear, studio);
    }

    List<Movie> findDistinctByGenreIsIn(List<Genre> genre);

    List<Movie> findDistinctByReleaseYearIs(Integer releaseYears);

    List<Movie> findDistinctByMpaaRatingIsIn(List<MpaaRating> mpaaRatings);

    List<Movie> findDistinctByAverageRatingIsGreaterThanEqual(Double rating);

}
