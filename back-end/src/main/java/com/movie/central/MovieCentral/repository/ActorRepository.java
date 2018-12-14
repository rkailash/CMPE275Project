package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.model.Actor;
import com.movie.central.MovieCentral.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	public Actor findActorByName(String name);
    public Actor findById(Long id);
    Actor findOneByName(String name);

    List<Actor> findDistinctByNameContainingIgnoreCase(String name);

    default Set<Movie> findMoviesByActor(String actorName){
        Set<Movie> movies = new HashSet<>();
        List<Actor> actors = findDistinctByNameContainingIgnoreCase(actorName);
        for(Actor actor : actors){
            movies.addAll(actor.getMovies());
        }
        return movies;
    }

    default Set<Movie> findMoviesByActors(String actorName){
        Set<Movie> movies = new HashSet<>();
        Set<Actor> actors = new HashSet<>();
        actors.addAll(findDistinctByNameContainingIgnoreCase(actorName));
        for(Actor actor : actors){
            movies.addAll(actor.getMovies());
        }
        return movies;
    }





}
