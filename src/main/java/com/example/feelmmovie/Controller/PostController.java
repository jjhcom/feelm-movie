package com.example.feelmmovie.Controller;


import com.example.feelmmovie.Model.Actor;
import com.example.feelmmovie.Model.Director;
import com.example.feelmmovie.Model.Movie;
import com.example.feelmmovie.Repository.ActorRepository;
import com.example.feelmmovie.Repository.DirectorRepository;
import com.example.feelmmovie.Repository.MovieRepository;
import com.example.feelmmovie.Service.PostService;
import com.example.feelmmovie.Service.SearchService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    private final PostService postService;
    SearchService api_movie = new SearchService();
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;

    @GeneratedValue
    @ResponseBody
    @RequestMapping(value = "/movie_search", method = RequestMethod.POST)
    public List<Movie> getMovie(@RequestParam("keyword") String keyword) throws Exception {
        List<Movie> titleContains = movieRepository.findByMovieTitleContains(keyword);
        List<Movie> ActorDirectorContains = movieRepository.findByMovieActorStrContainsOrMovieDirectorStrContains(keyword, keyword);

        if (titleContains.isEmpty() == false) {
            return postService.searchContentTitle(keyword);

        } else if (titleContains.isEmpty() == true && ActorDirectorContains.isEmpty() == false) {
            return postService.searchContent(keyword);
        } else {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(api_movie.search(keyword));
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray movie_result = (JSONArray) jsonObj.get("items");

            for (int i = 0; i < movie_result.size(); i++) {
                JSONObject res = (JSONObject) movie_result.get(i);
                String movie_title = (String) res.get("title");
                String movie_director = (String) res.get("director");
                String[] directors = movie_director.split("\\|");
                String movie_actor = (String) res.get("actor");
                String[] actors = movie_actor.split("\\|");
                String movie_image = (String) res.get("image");
                String movie_pubdate = (String) res.get("pubDate");

                List<Actor> actorTableList = new ArrayList<>();
                List<Director> directorTableList = new ArrayList<>();

                final Movie movieTable = Movie.builder()
                        .movieTitle(movie_title)
                        .movieActor(actorTableList)
                        .movieDirector(directorTableList)
                        .movieDirectorStr(movie_director)
                        .movieActorStr(movie_actor)
                        .movieImage(movie_image)
                        .moviePubdate(movie_pubdate)
                        .build();
                movieRepository.save(movieTable);

                for (int j = 0; j < directors.length; j++) {
                    final Director directorTable = Director.builder()
                            .directorName(directors[j])
                            .movie(movieTable)
                            .build();
                    directorRepository.save(directorTable);
                    directorTableList.add(directorTable);
                }

                for (int j = 0; j < actors.length; j++) {
                    final Actor actorTable = Actor.builder()
                            .actorName(actors[j])
                            .movie(movieTable)
                            .build();
                    actorRepository.save(actorTable);
                    actorTableList.add(actorTable);
                }

            }
            return  postService.searchContentTitle(keyword);
        }
    }

}
