package ru.lukashev.vote.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.lukashev.vote.DishTestData;
import ru.lukashev.vote.RestaurantTestData;
import ru.lukashev.vote.TestUtil;
import ru.lukashev.vote.json.JsonUtil;
import ru.lukashev.vote.model.Dish;
import ru.lukashev.vote.model.Restaurant;
import ru.lukashev.vote.repository.DishRepository;
import ru.lukashev.vote.repository.RestaurantRepository;
import ru.lukashev.vote.to.DishTo;
import ru.lukashev.vote.to.RestaurantTo;
import ru.lukashev.vote.util.DishUtil;
import ru.lukashev.vote.util.RestaurantUtil;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lukashev.vote.DishTestData.*;
import static ru.lukashev.vote.RestaurantTestData.*;
import static ru.lukashev.vote.UserTestData.ADMIN;
import static ru.lukashev.vote.TestUtil.userHttpBasic;



public class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/admin/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Test
    void testGetAllRestaurants() throws Exception{

        RESTAURANT1.setMenu(List.of( DISH1, DISH11, DISH6,DISH9,DISH10));
        RESTAURANT2.setMenu(List.of( DISH8,DISH2));
        RESTAURANT3.setMenu(List.of( DISH7,DISH3));
        RESTAURANT4.setMenu(List.of( DISH4));
        RESTAURANT5.setMenu(List.of( DISH5));

        mockMvc.perform(get(REST_URL) .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJson(RESTAURANT2, RESTAURANT4,RESTAURANT1,RESTAURANT5,RESTAURANT3));
    }

    @Test
    void testGetRestaurant() throws Exception{

        RESTAURANT1.setMenu(List.of( DISH1, DISH11, DISH6,DISH9,DISH10));

        mockMvc.perform(get(REST_URL + "/" + RESTAURANT1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJson(RESTAURANT1));
    }


    @Test
    void testDeleteRestaurant() throws Exception{

        mockMvc.perform(delete(REST_URL + "/" + RESTAURANT1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isNoContent());

        RestaurantTestData.assertMatch(restaurantRepository.getAll(), List.of(RESTAURANT2, RESTAURANT4,RESTAURANT5,RESTAURANT3));
    }

    @Test
    void testCreateRestaurantWithLocation() throws Exception{

        RestaurantTo restaurantTo = new RestaurantTo(null, "Camomile");

        ResultActions action = mockMvc.perform(post(REST_URL).with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurantTo)))
                .andExpect(status().isCreated());

       Restaurant returned = TestUtil.readFromJsonResultActions(action, Restaurant.class);
     Restaurant created = RestaurantUtil.createNewFromTo(restaurantTo);
        created.setId(returned.getId());

        RestaurantTestData.assertMatch(returned, created);
        RestaurantTestData.assertMatch(restaurantRepository.getAll(), created, RESTAURANT2, RESTAURANT4,RESTAURANT1,RESTAURANT5,RESTAURANT3);
    }

    @Test
    void testGetAllDishesOfTheRestaurant()throws Exception{

        mockMvc.perform(get(REST_URL + "/" + 100002 + "/dishes").with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJson(DISH1, DISH11, DISH6, DISH9, DISH10));
    }

    @Test
    void testGetDishOfTheRestaurant() throws Exception{
        mockMvc.perform(get(REST_URL + "/" + 100002 + "/dishes/" + DISH1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJson(DISH1));
    }

    @Test
    void deleteDishOfTheRestaurant() throws Exception{

        mockMvc.perform(delete(REST_URL + "/" + 100002 + "/dishes/" + DISH1_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isNoContent());

       DishTestData.assertMatch(dishRepository.getAll(RESTAURANT1_ID),  List.of(DISH11, DISH6,DISH9,DISH10));
    }

   @Test
    void createDishOfTheRestaurantWithLocation()throws Exception{
       DishTo dishTo = new DishTo(null, "herring", 90);

       ResultActions action = mockMvc.perform(post(REST_URL + "/" + 100002 + "/dishes/").with(userHttpBasic(ADMIN))
               .contentType(MediaType.APPLICATION_JSON)
               .content(JsonUtil.writeValue(dishTo)))
               .andExpect(status().isCreated());

       Dish returned = TestUtil.readFromJsonResultActions(action, Dish.class);
       Dish created = DishUtil.createNewFromTo(dishTo);
       created.setId(returned.getId());

       DishTestData.assertMatch(returned, created);
      DishTestData.assertMatch(dishRepository.getAll(RESTAURANT1_ID),List.of( DISH1, created, DISH11, DISH6,DISH9,DISH10) );
    }

    @Test
    void updateDishOfTheRestaurant() throws Exception{

       DishTo updatedTo = new DishTo(DISH1_ID, "okroshka", 100);
        mockMvc.perform(put(REST_URL + "/" + 100002 + "/dishes/" + DISH1_ID).with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

       DishTestData.assertMatch(dishRepository.get(DISH1_ID, RESTAURANT1_ID), DishUtil.updateFromTo(new Dish(DISH1_ID, DISH1.getName(), DISH1.getPrice()), updatedTo));
    }

    @Test
    void testGetAllEnabled()throws Exception{

        mockMvc.perform(get(REST_URL + "/" + 100002 + "/dishes/enabled").with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJson(DISH1, DISH9, DISH10));
    }

    @Test
    void testSetEnabled() throws Exception{

        mockMvc.perform(put(REST_URL + "/" + 100002 + "/dishes "+ 100012 +"/enabled?enabled=true").with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DishTestData.assertMatch(dishRepository.getEnabled(RESTAURANT1_ID), List.of(DISH1,DISH6, DISH9, DISH10));
    }
}
