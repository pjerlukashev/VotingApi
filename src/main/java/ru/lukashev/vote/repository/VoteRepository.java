package ru.lukashev.vote.repository;

import ru.lukashev.vote.model.Vote;
import java.time.LocalDate;
import java.util.List;



public interface VoteRepository {

    Vote get(int userId, LocalDate date);

    List<Vote> getAllForRestaurant(int restaurantId);

    List<Vote>  getAll();

    List<Vote> getVotingResults(LocalDate date);

    void delete(int id);

    void deleteAllOnDate(LocalDate date);

    Vote save(Vote vote);
}
