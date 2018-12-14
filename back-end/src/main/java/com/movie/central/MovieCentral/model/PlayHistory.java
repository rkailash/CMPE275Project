package com.movie.central.MovieCentral.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "play_history")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayHistory  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="movie_id")
    private Movie movie;

    @Column(name="play_time")
    private LocalDateTime playTime;

}
