package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.model.Director;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.repository.DirectorRepository;
import com.movie.central.MovieCentral.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectorService {
    @Autowired
    private DirectorRepository directorRepository;
    @Autowired
    private MovieService movieService;

    public Director processDirector(String directorName){
        Director director = directorRepository.findByName(directorName);

        if(director == null){
            Director savedDirector = Director.builder().name(directorName).build();
            director = directorRepository.save(savedDirector);
            //actorRepository.refresh(savedActor);
        }
        return director;
    }

    public void assignMovieToDirector(Director director, Movie movie) throws Exception{
        try{

            List<Movie> movies = new ArrayList<Movie>();
            if(!director.getMovies().isEmpty())
                movies = director.getMovies();
            //Movie currentMovie = movieService.getMovieById(id);
            movies.add(movie);
            director.setMovies(movies);
            directorRepository.save(director);
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();

        }

    }
}
