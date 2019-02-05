package ru.lukashev.vote.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.lukashev.vote.RestaurantTestData;
import ru.lukashev.vote.TimingExtension;
import ru.lukashev.vote.VoteTestData;
import ru.lukashev.vote.model.Vote;
import ru.lukashev.vote.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.lukashev.vote.RestaurantTestData.*;
import static ru.lukashev.vote.UserTestData.*;
import static ru.lukashev.vote.UserTestData.USER;
import static ru.lukashev.vote.VoteTestData.VOTE1;
import static ru.lukashev.vote.VoteTestData.VOTE1_ID;
import static ru.lukashev.vote.VoteTestData.VOTE2;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-db.xml"
})

@ExtendWith(TimingExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class VoteRepositoryTest {

    @Autowired
    private Environment env;

    @Autowired
    private VoteRepository repository;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Test
    void get() throws Exception{
        Vote vote = repository.get(ADMIN_ID, LocalDate.now());
        VoteTestData.assertMatch(vote, VOTE2 );
    }

    @Test
    void getAll()throws Exception{
        List<Vote> votes = repository.getAll();
        VoteTestData.assertMatch(votes, List.of(VOTE1,VOTE2) );
    }

    @Test
    void delete()throws Exception{
        repository.delete(VOTE1_ID);
        List<Vote> votes = repository.getAll();
        VoteTestData.assertMatch(votes, List.of(VOTE2) );
    }

    @Test
    void create() throws Exception{
        repository.delete(VOTE1_ID);
        Vote newVote = new Vote( USER, RESTAURANT5);
        Vote created = repository.save(newVote, USER_ID);
        newVote.setId(created.getId());
        VoteTestData.assertMatch(repository.getAll(), newVote, VOTE2);
    }

    @Test
    void update() throws Exception{
        Vote updated = new Vote(VOTE1);
        updated.setRestaurant(RESTAURANT3);
        repository.save(updated, USER_ID);
        VoteTestData.assertMatch(repository.get(USER_ID,LocalDate.now()), updated);
    }

    @Test
    void deleteNotFound() throws Exception{
        assertThrows(NotFoundException.class, ()->repository.delete(10));
    }

    @Test
    void getNotFound()throws Exception{
        assertThrows(NotFoundException.class, ()-> repository.get(10, LocalDate.now()));
    }

    @Test
    void updateNotFound()throws Exception{
        Vote updated = new Vote(VOTE1);
        assertThrows(NotFoundException.class, ()->repository.save(updated, ADMIN_ID));
    }

  /*  @Test
    void getVotingResults()throws Exception{
        List<Vote> votes = repository.getVotingResults(LocalDate.now());
        VoteTestData.assertMatch(votes, VOTE2, VOTE1 );
    }*/

    @Test
    void deleteOnDate()throws Exception{
        repository.deleteAllOnDate(LocalDate.now());
        VoteTestData.assertMatch(repository.getAll(), Collections.emptyList());
    }

    /*@Test
    void getAllForRestaurant()throws Exception{
        repository.getAllForRestaurant(RESTAURANT1_ID);
        VoteTestData.assertMatch(repository.getAll(), List.of(VOTE1));
    }*/

    @Test
    void getLog() throws Exception{
        VoteTestData.assertMatch(repository.getUserVotesLog(USER_ID), List.of(VOTE1) );
        RestaurantTestData.assertMatch(repository.getUserVotesLog(USER_ID).get(0).getRestaurant(), RESTAURANT1);
    }
}
