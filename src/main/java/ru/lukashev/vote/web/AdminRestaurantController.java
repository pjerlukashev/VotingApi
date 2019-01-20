package ru.lukashev.vote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lukashev.vote.model.Dish;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.repository.DishRepository;
import ru.lukashev.vote.repository.JpaRestaurantRepository;
import ru.lukashev.vote.util.ValidationUtil;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(AdminRestaurantController.REST_URL)
public class AdminRestaurantController {

    static final String REST_URL = "/rest/admin/restaurants";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants() {
        log.info("get all restaurants");
        return restaurantRepository.getAll();
    }

    @GetMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@PathVariable("restaurantId") int id) {
        log.info("get restaurant {}", id);
        return restaurantRepository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurantWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteRestaurant(@PathVariable("restaurantId") int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.delete(id);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable("restaurantId") int id) {
        log.info("update restaurant {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }


    @GetMapping(value = "dishes/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllDishesOfTheRestaurant(@PathVariable ("restaurantId") int id) {
        log.info("get all dishes of the restaurant");
        return dishRepository.getAll(id);
    }

    @GetMapping(value = "dishes/{restaurantId}/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDishOfTheRestaurant(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId ) {
        log.info("get dish {} of the restaurant {}", dishId,restaurantId );
        return dishRepository.get(dishId, restaurantId);
    }


    @PostMapping(value = "/dishes/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDishOfTheRestaurantWithLocation(@RequestBody Dish dish, @PathVariable ("restaurantId") int id) {
        log.info("create dish {} of the restaurant with id{}", dish);
        Dish created = dishRepository.save(dish, id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/dishes/{restaurantId}/{dishId}" )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDishOfTheRestaurant(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("delete dish {} of the restaurant {}", dishId, restaurantId);
       dishRepository.delete(dishId, restaurantId);
    }

    @PutMapping(value = "/dishes/{restaurantId}/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDishOfTheRestaurant(@RequestBody Dish dish, @PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId ) {
        log.info("update dish {} of the restaurant with id={}", dish, restaurantId);
        ValidationUtil.assureIdConsistent(dish, dishId);
        dishRepository.save(dish, restaurantId);
}
}
