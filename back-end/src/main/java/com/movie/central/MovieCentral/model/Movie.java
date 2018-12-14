package com.movie.central.MovieCentral.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.movie.central.MovieCentral.enums.Genre;
import com.movie.central.MovieCentral.enums.MpaaRating;
import com.movie.central.MovieCentral.enums.MovieType;
import com.movie.central.MovieCentral.enums.Status;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "movie")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="genre", nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name="release_year", nullable = false)
    private Integer releaseYear;

    @Column(name="studio", nullable = false)
    private String studio;

    @Column(name="synopsys", nullable = false, length = 1000)
    private String synopsys;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @Column(name="movie_url", nullable = false)
    private String movieUrl;

    @Column(name="country", nullable = false)
    private String country;

    @Column(name="average_rating", nullable = false)
    private Double averageRating;

    @Column(name="type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieType type;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name="mpaa_rating", nullable = false)
    @Enumerated(EnumType.STRING)
    private MpaaRating mpaaRating;

    @Column(name="pay_per_view_price")
    private Double payPerViewPrice;

    @ManyToMany
    @JoinTable(name="movie_actor",
            joinColumns = { @JoinColumn(name="MOVIE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name="ACTOR_ID", referencedColumnName = "ID")})
    @JsonManagedReference
    private List<Actor> actors;

    @ManyToOne
    @JoinColumn(name="director_id")
    @JsonManagedReference
    private Director director;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "customerRatingId.movie")
    @JsonManagedReference
    @JsonUnwrapped
    private List<CustomerRating> ratings;

    @Override
    public boolean equals(Object obj) {
        Movie movie = (Movie) obj;
        return movie.getId().equals(this.getId());
    }
}
