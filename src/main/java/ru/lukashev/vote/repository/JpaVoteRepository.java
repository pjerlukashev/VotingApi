package ru.lukashev.vote.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lukashev.vote.model.User;
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
        Assert.notNull(date, "date must not be null");
        List<Vote> votes = em.createNamedQuery(Vote.GET, Vote.class)
                .setParameter("date", date).setParameter("userId", userId)
                .getResultList();
        return ValidationUtil.checkNotFound(DataAccessUtils.singleResult(votes), "userId=" +userId + "date=" + date );
    }

    @Override
    public List<Vote> getAll() {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class).getResultList();
    }

    @Override
    @Transactional
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(em.createNamedQuery(Vote.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0, id);
    }

    @Override
    @Transactional
    public void deleteAllOnDate(LocalDate date) {
        Assert.notNull(date, "date must not be null");
      if (em.createNamedQuery(Vote.DELETE_ALL_ON_DATE).setParameter("date", date).executeUpdate()==0) {
          throw new NotFoundException("No entities found with date=" + date);
      }
    }

    @Override
    public  List<Vote> getUserVotesLog(int userId) {
       return  em.createNamedQuery(Vote.ALL_FOR_USER, Vote.class).setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int userId) {
        Assert.notNull(vote, "vote must not be null");
        Vote result;
        Vote stored;
        try {
             stored = get(userId, vote.getDate());
        }catch (NotFoundException e){stored = null; }

        if(!vote.isNew() && stored == null){ ValidationUtil.checkNotFoundWithId(null, vote.getId()); }
        if(!vote.isNew() && !stored.getId().equals(vote.getId())){ ValidationUtil.checkNotFoundWithId(null, vote.getId()); }
        vote.setUser(em.getReference(User.class, userId));
        if (vote.isNew()){
            em.persist(vote);
            result = vote;
        }
        else{
            result = em.merge(vote);
        }
        return ValidationUtil.checkNotFoundWithId(result, result.getId());
    }
}
