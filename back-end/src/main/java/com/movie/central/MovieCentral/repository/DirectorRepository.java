package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.model.Actor;
import com.movie.central.MovieCentral.model.Director;
import com.movie.central.MovieCentral.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface DirectorRepository extends JpaRepository<Director, Long>{

    List<Director> findDistinctByNameContainingIgnoreCase(String name);
	public Director findByName(String name);

    default Set<Movie> findMoviesByDirector(String directorName){
        Set<Movie> movies = new HashSet<>();
        List<Director> directors = findDistinctByNameContainingIgnoreCase(directorName);
        for(Director director : directors){
            movies.addAll(director.getMovies());
        }
        return movies;
    }

}
