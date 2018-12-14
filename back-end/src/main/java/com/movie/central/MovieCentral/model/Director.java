package com.movie.central.MovieCentral.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "director")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Director implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "director")
    @JsonBackReference
    private List<Movie> movies;

    public Director(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        Director director = (Director) obj;
        return director.getId().equals(this.getId());
    }

}
