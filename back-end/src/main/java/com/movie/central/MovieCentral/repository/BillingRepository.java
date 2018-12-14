package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.enums.SubscriptionType;
import com.movie.central.MovieCentral.model.Billing;
import com.movie.central.MovieCentral.model.Customer;
import com.movie.central.MovieCentral.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long>, JpaSpecificationExecutor<Billing>{

    List<Billing> findDistinctBySubscriptionTypeAndStartTimeGreaterThanEqualAndStartTimeLessThanEqual(SubscriptionType type, LocalDateTime startDateTime, LocalDateTime endTime);

    @Query(value = "select p.customer_id, m.name, count(p.customer_id) as playcount from play_history p, customer m where p.customer_id = m.id group by customer_id order by playcount limit 10", nativeQuery = true)
    List<Object[]> getMostActiveCustomers();
    List<Billing> findDistinctByCustomerAndMovieAndPlayCountExhausted(Customer customer, Movie movie, boolean playCount);

    List<Billing> findDistinctByCustomerAndEndTimeGreaterThan(Customer customer, LocalDateTime now);

}
