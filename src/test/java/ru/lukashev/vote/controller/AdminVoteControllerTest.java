package ru.lukashev.vote.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.lukashev.vote.VoteTestData;
import ru.lukashev.vote.repository.VoteRepository;
import ru.lukashev.vote.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lukashev.vote.TestUtil.userHttpBasic;
import static ru.lukashev.vote.UserTestData.ADMIN;


public class AdminVoteControllerTest extends  AbstractControllerTest {

    private static final String REST_URL = "/rest/admin/votes";

    @Autowired
    VoteRepository repository;

    @Test
    void testDeleteOnDate() throws Exception {
        mockMvc.perform(delete(REST_URL).param("startDate", DateTimeUtil.toString(LocalDate.now())).with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        VoteTestData.assertMatch(repository.getAll(), Collections.emptyList());
    }


}
