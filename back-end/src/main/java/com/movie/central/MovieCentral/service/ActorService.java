package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.model.Actor;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.repository.ActorRepository;
import com.movie.central.MovieCentral.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorService {
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    public Actor processActor(String actorName){
        Actor actor = actorRepository.findActorByName(actorName);

        if(actor == null){
            Actor savedActor = Actor.builder().name(actorName).build();
            actor = actorRepository.save(savedActor);
            //actorRepository.refresh(savedActor);
        }
        return actor;
    }

//    public void assignMovieToActor(Actor actor, Long movieId) throws Exception{
//
//        //if(newActor.isPresent()){
//        try{
//            Actor newActor = actorRepository.findById((actor.getId()));
//            //Actor a = newActor;//.get();
//            Optional<Movie> movie = movieRepository.findById(movieId);
//            List<Movie> movies ;//= new ArrayList<Movie>();
//            movies = newActor.getMovies();
//            if(movie.isPresent()){
//                Movie m = movie.get();
//                movies.add(m);
//            }
//
//            newActor.setMovies(movies);
//            actorRepository.save(newActor);
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//                //}

   // }
}
