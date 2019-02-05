package ru.lukashev.vote.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.lukashev.vote.TestUtil;
import ru.lukashev.vote.UserTestData;
import ru.lukashev.vote.json.JsonUtil;
import ru.lukashev.vote.model.Roles;
import ru.lukashev.vote.model.User;
import ru.lukashev.vote.repository.UserRepository;
import ru.lukashev.vote.to.UserTo;
import ru.lukashev.vote.util.UserUtil;
import ru.lukashev.vote.web.AdminUserController;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lukashev.vote.UserTestData.*;

public class AdminUserControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/rest/admin/users";

    @Autowired
    UserRepository repository;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + ADMIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(ADMIN));
    }

    @Test
    void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?email=" + USER.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(repository.getAll(), ADMIN);
    }

    @Test
    void testUpdate() throws Exception {
        UserTo updatedTo = new UserTo(100000, "newName", "newemail@ya.ru", "newPassword");
        mockMvc.perform(put(REST_URL + USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        UserTestData.assertMatch(repository.getByEmail("newemail@ya.ru"), UserUtil.updateFromTo(new User(USER), updatedTo));
}

    @Test
    void testCreate() throws Exception {
        UserTo createdTo = new UserTo(null, "newName", "newemail@ya.ru", "newPassword");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdTo)))
                .andExpect(status().isCreated());

        User returned = TestUtil.readFromJsonResultActions(action, User.class);
        User created = UserUtil.createNewFromTo(createdTo);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(repository.getAll(), ADMIN, created, USER);
    }

    @Test
    void testGetAll() throws Exception {
     mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
             .andExpect(contentJson(ADMIN, USER));
    }
}
