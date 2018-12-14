package com.movie.central.MovieCentral.model;

import com.movie.central.MovieCentral.enums.SubscriptionType;
import com.movie.central.MovieCentral.util.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="customer_id")
    Customer customer;

    @Column(name="start_time")
    private LocalDateTime startTime;

    @Column(name="end_time")
    private LocalDateTime endTime;

    @Column(name="total_amount")
    private Double totalAmount;

    @Column(name="subscription_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="movie_id")
    private Movie movie;

    @Column(name="playcount_exhausted", columnDefinition = "tinyint(1) default 0")
    private boolean playCountExhausted;

}
