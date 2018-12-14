package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.enums.UserRole;
import com.movie.central.MovieCentral.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

   // List<Object[]> findMovieAndPlayHistoryByCustomer_Id(Long id);

    List<Customer> findDistinctByUserRole(UserRole role);

    List<Customer> findDistinctByRegistrationDateTimeGreaterThanEqualAndRegistrationDateTimeLessThanEqual
            (LocalDateTime startDateTime, LocalDateTime endDateTime);

    Optional<Customer> findDistinctByEmail(String email);
}
