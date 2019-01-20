package ru.lukashev.vote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.model.User;
import ru.lukashev.vote.repository.RestaurantRepository;
import ru.lukashev.vote.repository.UserRepository;
import ru.lukashev.vote.util.VoteUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(UserController.REST_URL)
public class UserController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/user";

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants(){
        log.info("get all restaurants");
      return  restaurantRepository.getAll();
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable ("id") int id){
        log.info("get restaurant {}", id);
        return  restaurantRepository.get(id);
    }

    @PutMapping (consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> vote(@RequestBody Restaurant restaurant){
        LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(11,00))){
         return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        int authUserId = SecurityUtil.authUserId();
        log.info("user {} vote is {}", authUserId, restaurant);

      User user = userRepository.get(authUserId);
      user.setVote(restaurant);
      userRepository.save(user);
  return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Restaurant, Integer> getRatings(){
        log.info("get ratings");
        return VoteUtil.getRatings(userRepository.getAll());
    }

    @GetMapping(value="/percents", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Restaurant, Double> getRatingsInPercents(){
        log.info("get ratings in percents");
        return VoteUtil.getRatingsInPercents(userRepository.getAll());
    }

    @GetMapping(value="/winner", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getWinner(){
        log.info("get winner");
        return VoteUtil.getWinner(userRepository.getAll());
    }
}




