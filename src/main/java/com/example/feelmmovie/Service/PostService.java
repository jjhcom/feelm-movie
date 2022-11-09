package com.example.feelmmovie.Service;


import com.example.feelmmovie.Model.Movie;
import com.example.feelmmovie.Repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final MovieRepository movieRepository;
    public List<Movie> searchContentTitle(String keyword){
        return movieRepository.findByMovieTitleContains(keyword);
    }
    public List<Movie> searchContent(String keyword){
        return movieRepository.findByMovieActorStrContainsOrMovieDirectorStrContains(keyword, keyword);
    }

}
