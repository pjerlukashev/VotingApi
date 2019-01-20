package ru.lukashev.vote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.util.ValidationUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant result;
        if (restaurant.isNew()) {
            em.persist(restaurant);
            result = restaurant;
        } else {
            result = em.merge(restaurant);
        }
        return ValidationUtil.checkNotFoundWithId(result, restaurant.getId());
    }

    @Override
    public Restaurant get(int id) {
        return ValidationUtil.checkNotFoundWithId(em.find(Restaurant.class, id), id);
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.ALL_SORTED, Restaurant.class).getResultList();
    }

    @Override
    @Transactional
    public void delete(int id) {
         ValidationUtil.checkNotFoundWithId(em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0, id);
    }
}
