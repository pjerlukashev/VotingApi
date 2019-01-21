package ru.lukashev.vote.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lukashev.vote.model.Vote;
import ru.lukashev.vote.util.ValidationUtil;
import ru.lukashev.vote.util.exception.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVoteRepository implements VoteRepository  {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Vote get(int userId, LocalDate date) {
        Assert.notNull(date, "date must not b null");
        List<Vote> votes = em.createNamedQuery(Vote.GET, Vote.class)
                .setParameter("date", date).setParameter("userId", userId)
                .getResultList();
        return ValidationUtil.checkNotFound(DataAccessUtils.singleResult(votes), "userId=" +userId + "date=" + date );
    }

    @Override
    public List<Vote> getAllForRestaurant(int restaurantId) {
        return em.createNamedQuery(Vote.ALL_FOR_RESTAURANT, Vote.class).setParameter( "restaurantId ", restaurantId ).getResultList();
    }

    @Override
    public List<Vote> getAll() {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class).getResultList();
    }

    @Override
    public List<Vote> getVotingResults(LocalDate date) {
        return em.createNamedQuery(Vote.GET_VOTING_RESULTS, Vote.class).setParameter("date", date).getResultList();
    }

    @Override
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0, id);
    }

    @Override
    public void deleteAllOnDate(LocalDate date) {
      if (em.createNamedQuery(Vote.DELETE_ALL_ON_DATE, Vote.class).setParameter("date", date).executeUpdate()==0) {
          throw new NotFoundException("Not entities found with date=" + date);
      }
    }

    @Override
    public Vote save(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        Vote result;
        if (vote.isNew()) {
            em.persist(vote);
            result = vote;
        } else {
            result = em.merge(vote);
        }
        return ValidationUtil.checkNotFoundWithId(result, vote.getId());
    }
}
