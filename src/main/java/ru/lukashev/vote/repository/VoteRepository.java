package ru.lukashev.vote.repository;

import ru.lukashev.vote.model.Vote;
import java.time.LocalDate;
import java.util.List;



public interface VoteRepository {

    Vote get(int userId, LocalDate date);

    List<Vote>  getAll();

    void delete(int id);

    void deleteAllOnDate(LocalDate date);

    List<Vote> getUserVotesLog(int userId);

    Vote save(Vote vote, int userId);
}
