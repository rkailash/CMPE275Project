package com.movie.central.MovieCentral.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRatingId implements Serializable {
    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    Customer customer;

    @JsonBackReference
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    Movie movie;

    @Override
    public boolean equals(Object obj) {
        CustomerRatingId customerRatingId = (CustomerRatingId) obj;
        return customerRatingId.getCustomer().equals(this.getCustomer()) && customerRatingId.getMovie().equals(this.getMovie());
    }

    @Override
    public int hashCode() {
        return this.movie.hashCode() + this.customer.hashCode();
    }
}

