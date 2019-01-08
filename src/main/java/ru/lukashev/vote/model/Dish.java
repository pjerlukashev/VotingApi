package ru.lukashev.vote.model;

public class Dish extends AbstractNamedEntity {

    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
