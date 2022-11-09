package com.example.feelmmovie.Repository;

import com.example.feelmmovie.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByMovieActorStrContainsOrMovieDirectorStrContains(String movieActorStr, String movieDirectorStr);
    List<Movie> findByMovieTitleContains(String movieTitle);
}
