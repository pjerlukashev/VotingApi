package ru.lukashev.vote;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.lukashev.vote.json.JsonUtil;
import ru.lukashev.vote.model.Vote;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.lukashev.vote.RestaurantTestData.RESTAURANT1;
import static ru.lukashev.vote.RestaurantTestData.RESTAURANT2;
import static ru.lukashev.vote.UserTestData.ADMIN;
import static ru.lukashev.vote.UserTestData.USER;
import static ru.lukashev.vote.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final int VOTE1_ID = START_SEQ + 18;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER, RESTAURANT1 );
    public static final Vote VOTE2 = new Vote(VOTE1_ID+1, ADMIN, RESTAURANT2 );

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return content().json(JsonUtil.writeValue(List.of(expected)));
    }

    public static ResultMatcher contentJson(Vote expected) {
        return content().json(JsonUtil.writeValue(expected));
    }
}
