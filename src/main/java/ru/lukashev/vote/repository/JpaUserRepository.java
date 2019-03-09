package ru.lukashev.vote.repository;

import org.hibernate.jpa.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lukashev.vote.model.User;
import ru.lukashev.vote.util.ValidationUtil;
import ru.lukashev.vote.util.exception.NotFoundException;
import ru.lukashev.vote.web.LoggedUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("userRepository")
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository , UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }

    @Override
    @Transactional
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        User result;
        if (user.isNew()) {
            em.persist(user);
            result = user;
        } else {
            result = em.merge(user);
        }
        return ValidationUtil.checkNotFoundWithId(result, user.getId());
    }

    @Override
    @Transactional
    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0, id);
    }

    @Override
    public User get(int id) {
        return ValidationUtil.checkNotFoundWithId(em.find(User.class, id), id);
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter(1, email)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return ValidationUtil.checkNotFound(DataAccessUtils.singleResult(users), "email=" + email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u;
        try{u = getByEmail(email);}catch(NotFoundException e){throw new UsernameNotFoundException("User" + email + "is not found");}
        return new LoggedUser(u);
    }
}
