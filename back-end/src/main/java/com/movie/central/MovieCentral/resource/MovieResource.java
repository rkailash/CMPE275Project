package com.movie.central.MovieCentral.resource;

import com.movie.central.MovieCentral.model.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.central.MovieCentral.enums.Genre;
import com.movie.central.MovieCentral.enums.MovieType;
import com.movie.central.MovieCentral.enums.MpaaRating;
import com.movie.central.MovieCentral.enums.Status;
import com.movie.central.MovieCentral.exceptions.Error;
import com.movie.central.MovieCentral.exceptions.MovieCentralException;
import com.movie.central.MovieCentral.response.PlayDetails;
import com.movie.central.MovieCentral.response.Response;
import com.movie.central.MovieCentral.service.ActorService;
import com.movie.central.MovieCentral.service.DirectorService;

import com.movie.central.MovieCentral.service.MovieService;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.service.PlayHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.*;

//@Controller
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping("/movie")
@RestController
@RequestMapping("/api/movie")
public class MovieResource {

    @Autowired
    private MovieService movieService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private DirectorService directorService;
    @Autowired
    private PlayHistoryService playHistoryService;


//    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
//    public  ResponseEntity<?> addMovie (@RequestBody Movie movie) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//        movieService.addMovie(movie);
//        System.out.println("Saved");
//        return new ResponseEntity(null,HttpStatus.CREATED);
//    }

    @RequestMapping(value = "/editMovie", method = RequestMethod.POST)
    public ResponseEntity<?> editMovie(@RequestBody Map<String, String> input) throws Exception{
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        try {
            String title = input.get("title");
            Genre genre = Genre.valueOf(input.get("genre"));
            Integer releaseYear = Integer.parseInt(input.get("releaseYear"));
            Long id = Long.parseLong(input.get("id"));
            String studio = input.get("studio");
            String synopsys = input.get("synopsys");
            String imageUrl = input.get("imageUrl");
            String movieUrl = input.get("movieUrl");
            Status status = Status.valueOf(input.get("status"));
            Double averageRating = Double.parseDouble(input.get("averageRating"));
            String country = input.get("country");
            MovieType type = MovieType.valueOf(input.get("type"));
            Double price = Double.parseDouble(input.get("price"));
            MpaaRating mpaaRating = MpaaRating.valueOf(input.get("mpaaRating"));

            List<String> actorsList = Arrays.asList((input.get("actors")).split("\\s*,\\s*"));
            List<Actor> actors = new ArrayList<Actor>();
            for (String actor : actorsList) {
                Actor actorDetails = actorService.processActor(actor.toLowerCase());
                actors.add(actorDetails);
            }
            String director = input.get("director");
            Director saveDirector = directorService.processDirector(director.toLowerCase());
            Movie movie = Movie.builder().id(id).title(title).genre(genre).releaseYear(releaseYear).studio(studio).synopsys(synopsys).imageUrl(imageUrl).movieUrl(movieUrl)
                    .averageRating(averageRating).status(status).country(country).type(type).price(price).director(saveDirector).mpaaRating(mpaaRating).actors(actors).build();
            movieService.editMovie(movie);
            Response response = new Response("Movie Edited Successfully", HttpStatus.CREATED);
            return new ResponseEntity(response, response.getStatus());
        } catch(DataIntegrityViolationException ex){
            throw new MovieCentralException(Error.DUPLICATE_MOVIE);
        }
    }


    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    public ResponseEntity<?> addMovie(@RequestBody Map<String, String> input) throws Exception{
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        try {
            String title = input.get("title");
            Genre genre = Genre.valueOf(input.get("genre"));
            Integer releaseYear = Integer.parseInt(input.get("releaseYear"));
            String studio = input.get("studio");
            String synopsys = input.get("synopsys");
            String imageUrl = input.get("imageUrl");
            String movieUrl = input.get("movieUrl");
            Double averageRating = Double.parseDouble(input.get("averageRating"));
            String country = input.get("country");
            MovieType type = MovieType.valueOf(input.get("type"));
            Status status = Status.valueOf(input.get("status"));
            Double price = Double.parseDouble(input.get("price"));
            MpaaRating mpaaRating = MpaaRating.valueOf(input.get("mpaaRating"));

            List<String> actorsList = Arrays.asList((input.get("actors")).split("\\s*,\\s*"));
            List<Actor> actors = new ArrayList<Actor>();
            for (String actor : actorsList) {
                Actor actorDetails = actorService.processActor(actor.toLowerCase());
                actors.add(actorDetails);
            }
            String director = input.get("director");
            Director saveDirector = directorService.processDirector(director.toLowerCase());
            Movie movie = Movie.builder().title(title).genre(genre).releaseYear(releaseYear).studio(studio).synopsys(synopsys).imageUrl(imageUrl).movieUrl(movieUrl)
                    .averageRating(averageRating).status(status).country(country).type(type).price(price).director(saveDirector).mpaaRating(mpaaRating).actors(actors).build();
            movieService.addMovie(movie);
            Response response = new Response("Movie Added Successfully", HttpStatus.CREATED);
            return new ResponseEntity(response, response.getStatus());
        } catch(DataIntegrityViolationException ex){
            throw new MovieCentralException(Error.DUPLICATE_MOVIE);
        }
    }

    @RequestMapping(value = "/searchAll", method = RequestMethod.GET)
    public ResponseEntity<?> searchAllMovies(HttpSession session) throws Exception {
        List<Movie> movies = new ArrayList<>(movieService.findAllMovies());
        Map<String, List<Movie>> response = new HashMap<>();
        response.put("result", movies);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }



    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<?> filterByAttributeAndOrKeywords(@RequestBody MovieFilter movieFilter, HttpSession session) throws Exception{
        List<Movie> movies = movieService.filterMoviesByMovieFilter(movieFilter);
        Map<String, List<Movie>> response = new HashMap<>();
        response.put("result", movies);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public ResponseEntity<?> reviewMovie(@RequestBody Map<String,String> input, HttpSession session) throws Exception{
        Long customerId = Long.valueOf(input.get("customerId"));
        Long movieId = Long.valueOf(input.get("movieId"));
        Integer rating = Integer.valueOf(input.get("rating"));
        String ratingComment = input.get("comment");
        movieService.reviewMovie(customerId, movieId, rating, ratingComment);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public ResponseEntity<?> getAllReviews(@RequestParam Long movieId, HttpSession session) throws Exception{
        List<CustomerRating> ratings = movieService.getAllMovieRatings(movieId);
        Map<String, List<CustomerRating>> response = new HashMap<>();
        response.put("result", ratings);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }



    @RequestMapping(value = "/add-play-history", method = RequestMethod.POST)
    public ResponseEntity<?> addPlayHistory(@RequestBody Map<String, String> input) throws Exception{
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        try {
            Long customerId = Long.parseLong(input.get("customerId"));
            Long movieId = Long.parseLong(input.get("movieId"));
            playHistoryService.addPLayRecord(customerId, movieId);
            Response response = new Response("Play Record Added Successfully", HttpStatus.CREATED);
            return new ResponseEntity(response, response.getStatus());
        } catch(DataIntegrityViolationException ex){
            throw new MovieCentralException(Error.INVALID_CUSTOMER_OR_MOVIE);
        }
    }

    @RequestMapping(value = "/no_of_play_per_movie", method = RequestMethod.GET)
    public ResponseEntity<?> playPerMovie(HttpSession session) throws Exception{

        List<PlayDetails> play_details = movieService.getPlayPerMovie();

        Map<String,List<PlayDetails>> response = new HashMap<>();
        response.put("result" , play_details);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/most_played_movies", method = RequestMethod.GET)
    public ResponseEntity<?> mostPlayedMovies(HttpSession session) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        List<PlayDetails> play_details = movieService.getMostPlayedMovies();

        Map<String,List<PlayDetails>> response = new HashMap<>();
        response.put("result" , play_details);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/is_movie_play_allowed", method = RequestMethod.GET)
    public ResponseEntity<?> isMoviePlayAllowedForCustomer(@RequestParam Long customerId, @RequestParam Long movieId, HttpSession session)
            throws Exception{
        movieService.isMoviePlayAllowedForCustomer(customerId, movieId);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }



}
