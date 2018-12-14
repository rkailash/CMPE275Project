package com.movie.central.MovieCentral.repository;

import com.movie.central.MovieCentral.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Customer, Long>{


}
