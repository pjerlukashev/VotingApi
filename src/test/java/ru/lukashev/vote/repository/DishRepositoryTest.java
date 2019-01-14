package ru.lukashev.vote.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.lukashev.vote.DishTestData;
import ru.lukashev.vote.TimingExtension;
import ru.lukashev.vote.model.Dish;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.lukashev.vote.DishTestData.*;
import static ru.lukashev.vote.RestaurantTestData.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-db.xml"
})

@ExtendWith(TimingExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DishRepositoryTest {

    @Autowired
    private Environment env;

    @Autowired
    private DishRepository repository;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Test
    void delete() throws Exception {
        repository.delete(DISH1_ID, RESTAURANT1_ID);
        DishTestData.assertMatch(repository.getAll(RESTAURANT1_ID), List.of( DISH11, DISH6,DISH9,DISH10));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertFalse(repository.delete(DISH1_ID, 1));
    }

    @Test
    void create() throws Exception {
       Dish created = DishTestData.getCreated();
        repository.save(created, RESTAURANT1_ID);
        DishTestData.assertMatch(repository.getAll(RESTAURANT1_ID), List.of( DISH1, created, DISH11, DISH6,DISH9,DISH10) );
    }

    @Test
    void get() throws Exception {
        Dish actual = repository.get(DISH1_ID, RESTAURANT1_ID);
        DishTestData.assertMatch(actual, DISH1);
    }

    @Test
    void getNotFound() throws Exception {
        assertNull(repository.get(DISH1_ID, 100003));
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        repository.save(updated, RESTAURANT1_ID);
        DishTestData.assertMatch(repository.get(updated.getId(), RESTAURANT1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        DishTestData.assertMatch(repository.getAll(RESTAURANT1_ID), List.of( DISH1, DISH11, DISH6,DISH9,DISH10));
    }
}
