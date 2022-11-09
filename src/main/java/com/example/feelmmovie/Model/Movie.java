package com.example.feelmmovie.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "movie_table")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(length = 200)
    private String movieTitle;

    @OneToMany
    @JsonIgnore
    @JoinTable(name="movieDirector_table",
            joinColumns = @JoinColumn(name="movie_id"),
            inverseJoinColumns = @JoinColumn(name="director_id"))
    private List<Director> movieDirector = new ArrayList<>();


    @OneToMany
    @JsonIgnore
    @JoinTable(name="movieActor_table",
            joinColumns = @JoinColumn(name="movie_id"),
            inverseJoinColumns = @JoinColumn(name="actor_id"))
    private List<Actor> movieActor = new ArrayList<>();

    @Column(length = 200)
    private String movieDirectorStr;

    @Column(length = 200)
    private String movieActorStr;

    @Column(length = 200)
    private String movieImage;

    @Column(length = 200)
    private String moviePubdate;
}
