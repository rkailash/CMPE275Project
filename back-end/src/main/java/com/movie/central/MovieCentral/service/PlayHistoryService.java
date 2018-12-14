package com.movie.central.MovieCentral.service;


import com.movie.central.MovieCentral.exceptions.Error;
import com.movie.central.MovieCentral.exceptions.MovieCentralException;
import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.model.Movie;
import com.movie.central.MovieCentral.model.PlayHistory;
import com.movie.central.MovieCentral.repository.CustomerRepository;
import com.movie.central.MovieCentral.repository.MovieRepository;
import com.movie.central.MovieCentral.repository.PlayHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class PlayHistoryService {

    @Autowired
    PlayHistoryRepository playHistoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MovieRepository movieRepository;


    public void addPLayRecord(Long customerId, Long movieId) throws Exception{
        Optional<Customer> customer = customerRepository.findById(customerId);
        Movie movie = movieRepository.findMovieById(movieId);
        if(customer.isPresent() && movie != null){
            Customer c = customer.get();
            PlayHistory playHistory = new PlayHistory();
            playHistory.setCustomer(c);
            playHistory.setMovie(movie);
            LocalDateTime playTime = LocalDateTime.now(ZoneId.systemDefault());
            playHistory.setPlayTime(playTime);

            playHistoryRepository.save(playHistory);
        }else{
            throw new MovieCentralException(Error.INVALID_CUSTOMER_OR_MOVIE);
        }

    }
}
