package com.movie.central.MovieCentral.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "actor")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "actors")
    @JsonBackReference
    private List<Movie> movies;

    public Actor(String name){
        this.name = name;
    }


    @Override
    public boolean equals(Object obj) {
        Actor actor = (Actor) obj;
        return actor.getId().equals(this.getId());
    }
}
