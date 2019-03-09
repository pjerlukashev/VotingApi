package ru.lukashev.vote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lukashev.vote.model.Dish;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.repository.DishRepository;
import ru.lukashev.vote.repository.RestaurantRepository;
import ru.lukashev.vote.to.DishTo;
import ru.lukashev.vote.to.RestaurantTo;
import ru.lukashev.vote.util.DishUtil;
import ru.lukashev.vote.util.RestaurantUtil;
import ru.lukashev.vote.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(AdminRestaurantController.REST_URL)
public class AdminRestaurantController {

    static final String REST_URL = "/rest/admin/restaurants";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepository restaurantRepository;

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
    public ResponseEntity<Object> createRestaurantWithLocation(@RequestBody @Valid RestaurantTo restaurantTo, BindingResult result) {
        log.info("create restaurant {}", restaurantTo);
        if (result.hasErrors()) {
            return ValidationUtil.getResponseEntityWithErrorMessage(result);
        }
        Restaurant restaurant = RestaurantUtil.createNewFromTo(restaurantTo);
        ValidationUtil.checkNew(restaurant);
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

    @GetMapping(value = "/{restaurantId}/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllDishesOfTheRestaurant(@PathVariable ("restaurantId") int id) {
        log.info("get all dishes of the restaurant {}" , id);
        return dishRepository.getAll(id);
    }

    @GetMapping(value = "/{restaurantId}/dishes/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDishOfTheRestaurant(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId ) {
        log.info("get dish {} of the restaurant {}", dishId,restaurantId );
        return dishRepository.get(dishId, restaurantId);
    }


    @PostMapping(value = "{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDishOfTheRestaurantWithLocation(@RequestBody @Valid DishTo dishTo, @PathVariable ("restaurantId") int id, BindingResult result ) {
        log.info("create dish {} of the restaurant with id{}", dishTo);
        if (result.hasErrors()) {
            return ValidationUtil.getResponseEntityWithErrorMessage(result);
        }
        Dish dish = DishUtil.createNewFromTo(dishTo);
        ValidationUtil.checkNew(dish);
        Dish created = dishRepository.save(dish, id);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{restaurantId}/dishes/{dishId}" )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDishOfTheRestaurant(@PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId) {
        log.info("delete dish {} of the restaurant {}", dishId, restaurantId);
       dishRepository.delete(dishId, restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> updateDishOfTheRestaurant(@RequestBody @Valid DishTo dishTo, @PathVariable("restaurantId") int restaurantId, @PathVariable("dishId") int dishId, BindingResult result  ) {
        log.info("update dish {} of the restaurant with id={}", dishTo, restaurantId);
        if (result.hasErrors()) {
            return ValidationUtil.getResponseEntityWithErrorMessage(result);
        }
        Dish dish = DishUtil.updateFromTo(dishRepository.get(dishTo.getId(),restaurantId), dishTo);
        ValidationUtil.assureIdConsistent(dish, dishId);
        dishRepository.save(dish, restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value ="/{restaurantId}/dishes/enabled", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<DishTo> getAllEnabled(@PathVariable("restaurantId") int id){
        log.info("get all enabled dishes");
        List<Dish> dishes = dishRepository.getEnabled(id);
        List<DishTo> result = new ArrayList<>();
        for(Dish dish:dishes){result.add(DishUtil.asTo(dish));}
        return result;
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}/enabled")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void setEnabled(@RequestParam ("enabled") boolean enabled, @PathVariable("dishId") int dishId, @PathVariable("restaurantId") int restaurantId ){
        log.info(enabled ? "enable {}" : "disable {}", dishId);
        Dish dish = dishRepository.get(dishId, restaurantId);
        dish.setEnabled(enabled);
        dishRepository.save(dish, restaurantId);
    }
}
