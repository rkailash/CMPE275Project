package com.movie.central.MovieCentral.resource;

import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.response.MovieRatings;
import com.movie.central.MovieCentral.response.PlayDetails;
import com.movie.central.MovieCentral.response.PopularMovieDetails;
import com.movie.central.MovieCentral.service.MovieScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movie/scoreboard")
public class MovieScoreboardResource {

    @Autowired
    MovieScoreboardService movieScoreboardService;

    @RequestMapping(value = "/highlyRatedMovies", method = RequestMethod.GET)
    public ResponseEntity<?> getMostHighlyRatedMoviesInGivenMonth( HttpSession session) throws Exception {
        List<PopularMovieDetails> highlyRatedMoviesInGivenMonth = movieScoreboardService.getMostHighlyRatedMoviesInGivenMonth();
        Map<String, List<PopularMovieDetails>> response = new HashMap<>();
        response.put("result", highlyRatedMoviesInGivenMonth);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/mostPopularMovies", method = RequestMethod.GET)
    public ResponseEntity<?> getMostPopularMoviesInGivenMonth( HttpSession session) throws Exception {
        List<PopularMovieDetails> highlyRatedMoviesInGivenMonth = movieScoreboardService.getMostPopularMoviesInGivenMonth();
        Map<String, List<PopularMovieDetails>> response = new HashMap<>();
        response.put("result", highlyRatedMoviesInGivenMonth);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
