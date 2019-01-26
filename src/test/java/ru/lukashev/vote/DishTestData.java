package ru.lukashev.vote;

import ru.lukashev.vote.model.Dish;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lukashev.vote.model.AbstractNamedEntity.START_SEQ;

public class DishTestData {

    public static final int DISH1_ID = START_SEQ + 7;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Borsch", 80, true);
    public static final Dish DISH2 = new Dish(DISH1_ID + 1, "Solyanka", 120, true);
    public static final Dish DISH3 = new Dish(DISH1_ID + 2, "Pork", 160, false);
    public static final Dish DISH4 = new Dish(DISH1_ID + 3, "Roast", 170, false);
    public static final Dish DISH5 = new Dish(DISH1_ID + 4, "Eggs", 90, true);
    public static final Dish DISH6 = new Dish(DISH1_ID + 5, "Salad", 70, false);
    public static final Dish DISH7 = new Dish(DISH1_ID + 6, "Ice-cream", 60, true);
    public static final Dish DISH8 = new Dish(DISH1_ID + 7, "Chicken-soup", 90, true);
    public static final Dish DISH9 = new Dish(DISH1_ID + 8, "Salmon", 200, true);
    public static final Dish DISH10 = new Dish(DISH1_ID + 9, "Wok", 180, true);
    public static final Dish DISH11 = new Dish(DISH1_ID + 10, "Pizza", 150, false);

   public static Dish getCreated(){return new Dish(null, "Fish", 110);}
   public static Dish getUpdated(){return new Dish(DISH1_ID, "Fried Chicken", 110);}

  public static List<Dish> getDishes(){return List.of(DISH1, DISH2,DISH3, DISH4,DISH5,DISH6,DISH7,DISH8,DISH9,DISH10,DISH11);}

  public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
