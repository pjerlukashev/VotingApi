package ru.lukashev.vote;

import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.lukashev.vote.json.JsonUtil;
import ru.lukashev.vote.model.Restaurant;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.lukashev.vote.model.AbstractNamedEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT1_ID = START_SEQ + 2;

   public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Metropol");
   public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "Ivanyich");
   public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "U berez");
   public static final Restaurant RESTAURANT4 = new Restaurant(RESTAURANT1_ID + 3, "Koryushka");
   public static final Restaurant RESTAURANT5 = new Restaurant(RESTAURANT1_ID + 4, "MyLove");

  public static final List<Restaurant> getRestaurants(){return List.of(RESTAURANT2, RESTAURANT4,RESTAURANT1,RESTAURANT5,RESTAURANT3);}

  public static Restaurant getCreated(){return new Restaurant(null, "Hitch");}
  public static Restaurant getUpdated(){return new Restaurant(RESTAURANT1_ID, "Absolut");}

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "menu", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("menu","votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return content().json(JsonUtil.writeIgnoreProps(List.of(expected), "votes"));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return content().json(JsonUtil.writeIgnoreProps(expected,  "votes"));
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant... expected) {
        return result -> assertMatch(TestUtil.readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher getRestaurantMatcher(Restaurant expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Restaurant.class), expected);
    }


}
