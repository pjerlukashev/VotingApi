package ru.lukashev.vote.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import ru.lukashev.vote.model.AbstractBaseEntity;
import ru.lukashev.vote.model.Roles;
import ru.lukashev.vote.model.User;
import ru.lukashev.vote.to.UserTo;
import ru.lukashev.vote.util.UserUtil;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class LoggedUser implements UserDetails {

    private UserTo userTo;

protected int id = AbstractBaseEntity.START_SEQ;

protected Set<Roles> roles = Collections.singleton(Roles.ROLE_USER);

protected boolean enabled = true;

public LoggedUser(User u){
    id = u.getId();
roles=u.getRoles();
enabled = u.isEnabled();
this.userTo= UserUtil.asTo(u);
}

public static LoggedUser safeGet(){

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

if(auth==null){return null;}

Object user = auth.getPrincipal();

return (user instanceof LoggedUser) ? (LoggedUser) user: null;

}

//private static LoggedUser LOGGED_USER = new LoggedUser();

public static LoggedUser get(){

    LoggedUser loggedUser = safeGet();

    Assert.notNull(loggedUser, "No authorized user found");
    return loggedUser;
}

public static int id(){return get().userTo.getId();}

public Set<Roles> getAuthorities(){return roles;}

public boolean isEnabled(){return enabled;}

    @Override
    public String getPassword() {
        return userTo.getPassword();
    }

    @Override
    public String getUsername() {
        return userTo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }
}
