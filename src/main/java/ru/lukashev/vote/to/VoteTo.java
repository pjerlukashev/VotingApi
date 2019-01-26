package ru.lukashev.vote.to;

import java.io.Serializable;

public class VoteTo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private int userId;

    private int restaurantId;

    public VoteTo(){}

    public VoteTo(Integer id, int userId, int restaurantId) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
