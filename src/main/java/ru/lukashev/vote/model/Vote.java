package ru.lukashev.vote.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.lukashev.vote.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id"),
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v ORDER BY v.date, v.user.name DESC "),
        @NamedQuery(name = Vote.ALL_FOR_RESTAURANT, query = "SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId ORDER BY v.date DESC "),
        @NamedQuery(name = Vote.DELETE_ALL_ON_DATE, query = "DELETE FROM Vote v WHERE v.date=:date "),
        @NamedQuery(name = Vote.GET_VOTING_RESULTS, query = "SELECT v FROM Vote v WHERE v.date=:date "),
        @NamedQuery(name = Vote.GET, query = "SELECT v FROM Vote v WHERE v.date=:date AND v.user.id =: userId"),
        @NamedQuery(name = Vote.ALL_FOR_USER, query = "SELECT v FROM Vote v LEFT JOIN FETCH v.restaurant WHERE v.user.id=:userId  ORDER BY v.date DESC"),
})


@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "date_user_id_unique_idx")})
public class Vote extends AbstractBaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String ALL_SORTED = "Vote.getAllSorted";
    public static final String ALL_FOR_RESTAURANT = "Vote.getAllForRestaurant";
    public static final String DELETE_ALL_ON_DATE= "Vote.deleteAllOnDate";
    public static final String GET_VOTING_RESULTS= "Vote.getVotingResults";
    public static final String GET= "Vote.get";
    public static final String ALL_FOR_USER= "Vote.getAllForUser";

    @Column(name = "date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public Vote(){}

    public Vote(Integer id, User user, Restaurant restaurant) {
        this.id=id;
        this.date = LocalDate.now();
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(User user, Restaurant restaurant) {
       this(null, user, restaurant);
    }

    public Vote(Vote vote){this(vote.getId(),vote.getUser(),vote.getRestaurant());}

    public Vote(@NotNull LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
