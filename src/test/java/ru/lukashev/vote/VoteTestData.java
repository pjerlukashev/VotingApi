package ru.lukashev.vote;

import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.lukashev.vote.json.JsonUtil;
import ru.lukashev.vote.model.Vote;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.lukashev.vote.RestaurantTestData.*;
import static ru.lukashev.vote.UserTestData.*;
import static ru.lukashev.vote.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {

    public static final int VOTE1_ID = START_SEQ + 18;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER, RESTAURANT1 );
    public static final Vote VOTE2 = new Vote(VOTE1_ID+1, ADMIN, RESTAURANT2 );

    public static void assertMatch(Vote actual, Vote expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return content().json(JsonUtil.writeValue(List.of(expected)));
    }

    public static ResultMatcher contentJson(Vote expected) {
        return content().json(JsonUtil.writeValue(expected));
    }
}
