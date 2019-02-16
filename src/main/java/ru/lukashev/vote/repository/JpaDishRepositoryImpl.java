package ru.lukashev.vote.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lukashev.vote.model.Dish;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.util.ValidationUtil;
import ru.lukashev.vote.util.exception.NotFoundException;

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
        Assert.notNull(dish, "dish must not be null");
        Dish stored;
        Dish result;
        if(!dish.isNew()) {  try {
            stored = get(dish.getId(),restaurantId);
        }catch (NotFoundException e){stored = null; }
        if(stored==null)
        { ValidationUtil.checkNotFoundWithId(null, dish.getId()); }}
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if (dish.isNew()){
            em.persist(dish);
            result = dish;
        }
        else{
            result = em.merge(dish);
        }
        return ValidationUtil.checkNotFoundWithId(result, dish.getId());
    }

    @Override
    @Transactional
    public void delete(int id, int restaurantId) {
        ValidationUtil.checkNotFoundWithId(em.createNamedQuery(Dish.DELETE)
                .setParameter("id", id)
                .setParameter("restaurantId", restaurantId)
                .executeUpdate() != 0, id);
    }

    @Override
    public Dish get(int id, int restaurantId) {
        Dish result;
        Dish dish = em.find(Dish.class, id);
        result = dish != null &&dish.getRestaurant().getId() == restaurantId ? dish : null;
        return ValidationUtil.checkNotFoundWithId(result, id);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return em.createNamedQuery(Dish.ALL_SORTED, Dish.class)
                .setParameter("restaurantId",  restaurantId)
                .getResultList();
    }

    @Override
    public List<Dish> getEnabled(int restaurantId) {
        return  em.createNamedQuery(Dish.GET_ENABLED, Dish.class)
                .setParameter("restaurantId",  restaurantId).setParameter("enabled" , true)
                .getResultList();
    }
}
