package io.javabrains.moviecatalogservice.Resources;

import io.javabrains.moviecatalogservice.Models.CatalogItem;
import io.javabrains.moviecatalogservice.Models.Movie;
import io.javabrains.moviecatalogservice.Models.Rating;
import io.javabrains.moviecatalogservice.Models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;



    @RequestMapping("/{userId}")
    public List<CatalogItem> gatCatalog(@PathVariable("userId") String userId){



//        1. get all user rated movies
        UserRating ratings  = restTemplate
                .getForObject("http://localhost:8083/ratingdata/users/" + userId, UserRating.class);

        return ratings.getUserRating().stream()
                .map(rating-> {
/*                    Asynchronous form
                   Movie movie =  webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8082/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                          .block();
*/
//                    2. For each movie ID call Movie Info service and get details
                    Movie movie =  restTemplate
                           .getForObject("http://localhost:8082/movies/"
                           + rating.getMovieId(), Movie.class);

//                    3. Put all the info obtained together as list
                    return new CatalogItem(movie.getName(), "DESC", rating.getRating());
                })
                .collect(Collectors.toList());
    }
}
