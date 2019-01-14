package ru.lukashev.vote.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.lukashev.vote.RestaurantTestData;
import ru.lukashev.vote.TimingExtension;
import ru.lukashev.vote.model.Restaurant;

import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.lukashev.vote.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-db.xml"
})

@ExtendWith(TimingExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RestaurantRepositoryTest {

    @Autowired
    private Environment env;

    @Autowired
    private RestaurantRepository repository;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Test
    void delete() throws Exception {
        repository.delete(RESTAURANT1_ID);
        RestaurantTestData.assertMatch(repository.getAll(), RESTAURANT2, RESTAURANT4, RESTAURANT5, RESTAURANT3 );
    }

    @Test
    void deleteNotFound() throws Exception {
        assertFalse(repository.delete(20));
    }

    @Test
    void create() throws Exception {
        Restaurant created = getCreated();
        repository.save(created);
        assertMatch(repository.getAll(), created, RESTAURANT2, RESTAURANT4, RESTAURANT1, RESTAURANT5, RESTAURANT3);
    }

    @Test
    void get() throws Exception {
        Restaurant actual = repository.get(RESTAURANT1_ID);
        assertMatch(actual, RESTAURANT1);
    }

    @Test
    void getNotFound() throws Exception {
        assertNull(repository.get(20));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        repository.save(updated);
        assertMatch(repository.get(updated.getId()), updated);
    }

    @Test
    void getAll() throws Exception {
        assertMatch(repository.getAll(), RestaurantTestData.getRestaurants());
    }
}
