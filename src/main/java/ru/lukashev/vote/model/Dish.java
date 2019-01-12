package ru.lukashev.vote.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT m FROM Dish m WHERE m.restaurant.id=:restaurantId ORDER BY m.name ASC"),
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish m WHERE m.id=:id AND m.restaurant.id=:restaurantId"),
})

@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id"}, name = "dish_restaurant_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    public static final String ALL_SORTED = "Meal.getAll";
    public static final String DELETE = "Meal.delete";

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 5000)
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

   public Dish(){}

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}
