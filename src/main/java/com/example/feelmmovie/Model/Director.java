package com.example.feelmmovie.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "director_table")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "director_id")
    private Long id;

    @Column(length = 200)
    private String directorName;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;
}
