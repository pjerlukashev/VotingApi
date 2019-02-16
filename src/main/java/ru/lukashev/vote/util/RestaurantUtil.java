package ru.lukashev.vote.util;

import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.to.RestaurantTo;

public class RestaurantUtil {

    public static Restaurant createNewFromTo( RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName());
    }
}
