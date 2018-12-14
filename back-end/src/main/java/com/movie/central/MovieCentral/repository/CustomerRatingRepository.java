package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.model.CustomerRating;
import com.movie.central.MovieCentral.model.CustomerRatingId;
import com.movie.central.MovieCentral.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerRatingRepository extends JpaRepository<CustomerRating, Long> {
    List<CustomerRating> findDistinctByCustomerRatingIdCustomerAndCustomerRatingIdMovie(Customer customer, Movie movie);

    List<CustomerRating> findDistinctByCustomerRatingIdCustomer(Customer customer);

    List<CustomerRating> findDistinctByCustomerRatingIdMovie(Movie movie);

    @Query(value = "select m.id, m.title, avg(c.rating) as rating, m.average_rating from movie m join customer_rating c on c.movie_id = m.id and  rating_time between ?1 AND ?2 group by m.id order by rating desc limit 10", nativeQuery = true)
    List<Object[]> findTopTenByAverageRatingInLastMonth(LocalDateTime startDateTime, LocalDateTime endDateTime);



}
