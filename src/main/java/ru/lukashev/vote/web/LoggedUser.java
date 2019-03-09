package ru.lukashev.vote.web;

import ru.lukashev.vote.model.User;
import ru.lukashev.vote.to.UserTo;
import ru.lukashev.vote.util.UserUtil;

public class LoggedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public LoggedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}
