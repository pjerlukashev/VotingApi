package ru.lukashev.vote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.model.Vote;
import ru.lukashev.vote.repository.RestaurantRepository;
import ru.lukashev.vote.repository.UserRepository;
import ru.lukashev.vote.repository.VoteRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UserActionController.REST_URL)
public class UserActionController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/user";

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value="/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants(){
        log.info("get all restaurants");
      return  restaurantRepository.getAll();
    }

    @GetMapping(value="/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable ("id") int id){
        log.info("get restaurant {}", id);
        return  restaurantRepository.get(id);
    }

    @PutMapping (value="/votes/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> vote(@PathVariable("restaurantId") int restaurantId){
        int authUserId = SecurityUtil.authUserId();
        log.info("user {} vote is {}", authUserId, restaurantId);
        LocalTime now = LocalTime.now();
        if (now.isAfter(LocalTime.of(11,0))){
         return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Vote stored = voteRepository.get(authUserId, LocalDate.now());
        if(stored == null ) {
            voteRepository.save(new Vote(userRepository.get(authUserId), restaurantRepository.get(restaurantId)), authUserId);
        }else{
            stored.setRestaurant(restaurantRepository.get(restaurantId));
            voteRepository.save(stored, authUserId);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping(value="/myvotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getUserVoteLog(){
        int authUserId = SecurityUtil.authUserId();
        log.info("get vote log for user {}", authUserId);
        return voteRepository.getUserVotesLog(authUserId);
    }

    @GetMapping(value="/log/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public   Map<LocalDate, Integer> getVoteLogOnRestaurant(@PathVariable ("restaurantId") int id) {
        log.info("get vote log for restaurant {}", id);
        List<Vote> votes = voteRepository.getAll();
        return votes.stream().filter(vote-> vote.getRestaurant().getId().equals(id)).collect(Collectors.groupingBy(Vote::getDate, Collectors.summingInt(vote->1)));
    }
}




