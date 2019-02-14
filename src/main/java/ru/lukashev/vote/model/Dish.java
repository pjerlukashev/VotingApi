package ru.lukashev.vote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@NamedQueries({
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT m FROM Dish m WHERE m.restaurant.id=:restaurantId ORDER BY m.name ASC"),
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish m WHERE m.id=:id AND m.restaurant.id=:restaurantId"),
        @NamedQuery(name = Dish.GET_ENABLED, query = "SELECT m FROM Dish m WHERE m.restaurant.id=:restaurantId AND m.enabled =: enabled ORDER BY m.name ASC"),
})
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "restaurant_id"}, name = "dish_restaurant_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    public static final String ALL_SORTED = "Dish.getAll";
    public static final String DELETE = "Dish.delete";
    public static final String GET_ENABLED = "Dish.getEnabled";

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 5000)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "enabled", nullable = false)
    @NotNull
    private boolean enabled;

    @Column(name = "date", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate date;

   public Dish(){}

    public LocalDate getDate() {
        return date;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
        this.enabled = true;
        this.date= LocalDate.now();
   }

    public Dish(Integer id, String name, Integer price, boolean enabled) {
        super(id, name);
        this.price = price;
        this.enabled = enabled;
    }
    public Dish(String name, Integer price) {
        this(null, name, price);
    }

}
