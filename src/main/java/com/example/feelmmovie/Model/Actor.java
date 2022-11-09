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
@Entity(name = "actor_table")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long id;

    @Column(length = 200)
    private String actorName;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    @Column(length = 200)
    private String actorImage;
}
