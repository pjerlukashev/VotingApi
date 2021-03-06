package ru.lukashev.vote.repository;

import ru.lukashev.vote.model.Dish;

import java.util.List;


public interface DishRepository {

    Dish save(Dish dish, int restaurantId );

    boolean delete(int id, int restaurantId);

    Dish get(int id, int restaurantId);

    List<Dish> getAll(int restaurantId);
}
