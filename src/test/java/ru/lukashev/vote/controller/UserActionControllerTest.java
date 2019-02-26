package ru.lukashev.vote.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.lukashev.vote.RestaurantTestData;
import ru.lukashev.vote.VoteTestData;
import ru.lukashev.vote.json.JsonUtil;
import ru.lukashev.vote.model.Vote;
import ru.lukashev.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lukashev.vote.DishTestData.*;
import static ru.lukashev.vote.RestaurantTestData.*;
import static ru.lukashev.vote.TestUtil.userHttpBasic;
import static ru.lukashev.vote.UserTestData.*;
import static ru.lukashev.vote.VoteTestData.*;


public class UserActionControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/rest/user";

    @Autowired
    VoteRepository repository;

    @Test
    void testGetAllRestaurants() throws Exception{

        RESTAURANT1.setMenu(List.of( DISH1, DISH11, DISH6,DISH9,DISH10));
        RESTAURANT2.setMenu(List.of( DISH8,DISH2));
        RESTAURANT3.setMenu(List.of( DISH7,DISH3));
        RESTAURANT4.setMenu(List.of( DISH4));
        RESTAURANT5.setMenu(List.of( DISH5));

        mockMvc.perform(get(REST_URL + "/restaurants").with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJson(RESTAURANT2, RESTAURANT4,RESTAURANT1,RESTAURANT5,RESTAURANT3));
    }

    @Test
    void testGetRestaurant() throws Exception{
        RESTAURANT1.setMenu(List.of( DISH1, DISH11, DISH6,DISH9,DISH10));

        mockMvc.perform(get(REST_URL + "/restaurants/" + RESTAURANT1_ID).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJson(RESTAURANT1));
    }

    @Test
    void testVote() throws Exception{

        mockMvc.perform(put(REST_URL + "/vote" + RESTAURANT1_ID).with(userHttpBasic(USER)))
                .andExpect(status().isOk());

        VoteTestData.assertMatch(repository.get(USER_ID, LocalDate.now()), new Vote(100020, USER, RESTAURANT1) );
    }

    @Test
    void testGetUserVoteLog() throws Exception{

        mockMvc.perform(get(REST_URL + "/myvotes").with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.contentJson(VOTE1));
    }


    @Test
    void testGetVoteLogOnRestaurant() throws Exception{

        mockMvc.perform(get(REST_URL + "/log/" + RESTAURANT1_ID).with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(JsonUtil.writeValue(new HashMap<LocalDate, Integer>().put(LocalDate.now(), 1))));
}

}
