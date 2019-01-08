package ru.lukashev.vote.util;

import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class VoteUtil {

    public static Map<Restaurant, Integer> getRatings(List<User> users){
        Objects.requireNonNull(users, "users cannot be null");
        return users.stream().collect(Collectors.groupingBy(User::getVote,  Collectors.summingInt(user->  1 )));
    }

    public static Restaurant getWinner(List<User> users){
        Map<Restaurant, Integer> ratings = getRatings(users);
        return ratings.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }

    public static  Map<Restaurant, Double> getRatingsInPercents(List<User> users){
        Map<Restaurant, Integer> intermediateRatings = getRatings(users);
        int userCount = (int) users.stream().count();

        Map<Restaurant, Double> result = new HashMap<>();

        for(Map.Entry entry: intermediateRatings.entrySet()){
            Double value = ((double) Math.round ((((double)(int)entry.getValue())*100/userCount)*100))/100;
            result.put((Restaurant) entry.getKey(), value);
        }
        return result;
    }
}
