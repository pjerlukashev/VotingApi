package ru.lukashev.vote;

import ru.lukashev.vote.model.Roles;
import ru.lukashev.vote.model.User;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static ru.lukashev.vote.model.AbstractNamedEntity.START_SEQ;

public class UserTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static  User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Roles.ROLE_USER);
    public static  User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Roles.ROLE_ADMIN,  Roles.ROLE_USER);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
