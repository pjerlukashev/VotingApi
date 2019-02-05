package ru.lukashev.vote.util;

import ru.lukashev.vote.model.Dish;
import ru.lukashev.vote.to.DishTo;

import java.time.LocalDate;

public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo){
        return new Dish(null, dishTo.getName(), dishTo.getPrice());
    }

    public static Dish updateFromTo(Dish dish,DishTo dishTo){
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setDate(LocalDate.now());
        return dish;
    }

    public static DishTo asTo(Dish dish){
        return new DishTo(dish.getId(),dish.getName(), dish.getPrice());
    }
}
