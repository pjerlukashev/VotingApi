package ru.lukashev.vote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lukashev.vote.model.Dish;
import ru.lukashev.vote.model.Restaurant;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaDishRepositoryImpl implements DishRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {

        if(!dish.isNew() && get(dish.getId(), restaurantId)==null){return null;}
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if (dish.isNew()){
            em.persist(dish);
            return dish;
        }
        else{
            return em.merge(dish);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int restaurantId) {
        return em.createNamedQuery(Dish.DELETE)
                .setParameter("id", id)
                .setParameter("restaurantId", restaurantId)
                .executeUpdate() != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        Dish dish = em.find(Dish.class, id);
        return dish != null &&dish.getRestaurant().getId() == restaurantId ? dish : null;
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return em.createNamedQuery(Dish.ALL_SORTED, Dish.class)
                .setParameter("restaurantId",  restaurantId)
                .getResultList();
    }
}
