package ru.lukashev.vote.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.lukashev.vote.TimingExtension;
import ru.lukashev.vote.model.Roles;
import ru.lukashev.vote.model.User;
import ru.lukashev.vote.util.exception.NotFoundException;
import java.util.Collections;
import java.util.List;

import static ru.lukashev.vote.UserTestData.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-db.xml"
})

@ExtendWith(TimingExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Test
    void get() throws Exception{
        User user = repository.get(USER_ID);
       assertMatch(user, USER );
}

    @Test
    void getAll()throws Exception{
        List<User> users = repository.getAll();
        assertMatch(users, List.of(ADMIN, USER) );
    }

    @Test
    void delete()throws Exception{
        repository.delete(USER_ID);
        List<User> users = repository.getAll();
        assertMatch(users, List.of(ADMIN) );
    }

    @Test
    void create() throws Exception{
        User newUser = new User(null, "New", "new@gmail.com", "newPass",  Roles.ROLE_ADMIN, Roles.ROLE_USER);
        User created = repository.save(new User(newUser));
        newUser.setId(created.getId());
        assertMatch(repository.getAll(), ADMIN, newUser, USER);
    }

    @Test
    void getByEmail()throws Exception{
        User found = repository.getByEmail("user@yandex.ru");
        assertMatch(found, USER);
    }

    @Test
    void update() throws Exception{
        User updated = new User(USER);
        updated.setEmail("newemail@yandex.ru");
        updated.setPassword("newPassword");
        updated.setRoles(Collections.singletonList(Roles.ROLE_ADMIN));
       repository.save(updated);
       assertMatch(repository.get(updated.getId()), updated);
    }

   @Test
    void createDuplicatedByEmail()  throws Exception{
       Assertions.assertThrows(DataAccessException.class, () ->
               repository.save(new User(null, "Duplicate", "user@yandex.ru", "newPass", Roles.ROLE_USER)));
    }

   @Test
    void deleteNotFound() throws Exception{
       Assertions.assertThrows(NotFoundException.class, ()->repository.delete(10));
    }

    @Test
    void getNotFound()throws Exception{
        Assertions.assertThrows(NotFoundException.class, ()-> repository.get(10));
    }
}
