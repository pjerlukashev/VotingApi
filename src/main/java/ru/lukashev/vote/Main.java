package ru.lukashev.vote;

import org.slf4j.LoggerFactory;
import ru.lukashev.vote.model.Restaurant;
import org.slf4j.Logger;

public class Main {

    private static final Logger LOG  = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        Restaurant restaurant1 = new Restaurant(1, "Metropol");
        Restaurant restaurant2 = new Restaurant(2, "Ivanyich");
        Restaurant restaurant3 = new Restaurant(3, "U berez");
        Restaurant restaurant4 = new Restaurant(4, "Koryushka");
        Restaurant restaurant5 = new Restaurant(5, "MyLove");


    }
}
