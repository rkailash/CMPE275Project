package com.movie.central.MovieCentral.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_rating")
@Getter @Setter @NoArgsConstructor @Builder @AllArgsConstructor
public class CustomerRating implements Serializable{

    @EmbeddedId
    @JsonUnwrapped
    CustomerRatingId customerRatingId;

    @Column(name="rating")
    private Integer rating;

    @Column(name="rating_comment")
    private String ratingComment;

    @Column(name="reviewer_screenname")
    private String reviewerScreenName;

    @Column(name="rating_time")
    private LocalDateTime ratingTime;

}


