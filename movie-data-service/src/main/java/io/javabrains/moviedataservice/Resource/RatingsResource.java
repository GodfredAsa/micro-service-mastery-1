package io.javabrains.moviedataservice.Resource;

import io.javabrains.moviedataservice.Models.Rating;
import io.javabrains.moviedataservice.Models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingsResource {

    @RequestMapping( "/{movieId}")
    public Rating getRating(@PathVariable() String movieId){
        return new Rating(movieId, 4);
    }


    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("123", 4),
                new Rating("345", 3)
        );

        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;

    }
}
