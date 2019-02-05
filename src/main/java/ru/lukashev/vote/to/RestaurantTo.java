package ru.lukashev.vote.to;

public class RestaurantTo extends BaseTo {

    private String name;

    public RestaurantTo(){}

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
