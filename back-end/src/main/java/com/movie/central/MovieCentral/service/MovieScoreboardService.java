package com.movie.central.MovieCentral.service;

import com.movie.central.MovieCentral.model.CustomerRating;
import com.movie.central.MovieCentral.model.CustomerRatingId;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.repository.CustomerRatingRepository;
import com.movie.central.MovieCentral.repository.MovieRepository;
import com.movie.central.MovieCentral.repository.PlayHistoryRepository;
import com.movie.central.MovieCentral.response.MovieRatings;
import com.movie.central.MovieCentral.response.PlayDetails;
import com.movie.central.MovieCentral.response.PopularMovieDetails;
import com.movie.central.MovieCentral.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieScoreboardService {

    @Autowired
    CustomerRatingRepository customerRatingRepository;

    @Autowired
    PlayHistoryRepository playHistoryRepository;

    @Autowired
    MovieRepository movieRepository;

    public List<PopularMovieDetails> getMostHighlyRatedMoviesInGivenMonth(){

        LocalDateTime startDateTime = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime endDateTime = startDateTime.minusMonths(1);

        List<Object[]> ratings = customerRatingRepository.findTopTenByAverageRatingInLastMonth(endDateTime, startDateTime);
        List<PopularMovieDetails> playDetailsNew = new ArrayList<PopularMovieDetails>();
        try {
            if (ratings != null && ratings.size() > 0) {

                for (Object[] obj : ratings) {
                    PopularMovieDetails playDet = new PopularMovieDetails();
                    playDet.setId((BigInteger)obj[0]);
                    playDet.setName((String) obj[1]);
                    if(obj[2] != null)
                        playDet.setAverageRating(((BigDecimal) obj[2]).doubleValue());
                    else
                        playDet.setAverageRating(0.0);
                    Movie m = movieRepository.getOne(((BigInteger)obj[0]).longValue());
                    playDet.setAverageRating(m.getAverageRating());
                    playDet.setActors(m.getActors().stream().map(movie -> movie.getName()).collect(Collectors.toList()));
                    playDet.setDirector(m.getDirector().getName());
                    playDet.setGenre(m.getGenre());
                    playDetailsNew.add(playDet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playDetailsNew;
    }

    public List<PopularMovieDetails> getMostPopularMoviesInGivenMonth(){

        LocalDateTime startDateTime = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime endDateTime = startDateTime.minusMonths(1);

        List<Object[]> playDetails = playHistoryRepository.getTopTenMoviesPlayCountInMonth(endDateTime, startDateTime);
        List<PopularMovieDetails> playDetailsNew = new ArrayList<PopularMovieDetails>();

        try {
            if (playDetails != null && playDetails.size() > 0) {


                for (Object[] obj : playDetails) {
                    PopularMovieDetails playDet = new PopularMovieDetails();
                    playDet.setId((BigInteger)obj[0]);
                    playDet.setName((String) obj[1]);
                    playDet.setPlayCount((BigInteger)obj[2]);
                    playDet.setAverageRating((Double)obj[3]);
                    Movie m = movieRepository.getOne(((BigInteger)obj[0]).longValue());
                    playDet.setActors(m.getActors().stream().map(movie -> movie.getName()).collect(Collectors.toList()));
                    playDet.setDirector(m.getDirector().getName());
                    playDet.setGenre(m.getGenre());
                    playDetailsNew.add(playDet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playDetailsNew;
    }
}
