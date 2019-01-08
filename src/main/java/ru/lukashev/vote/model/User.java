package ru.lukashev.vote.model;

import java.util.Date;
import java.util.Set;

public class User extends AbstractNamedEntity  {

    private String email;

    private String password;

    private boolean enabled = true;

    private Date registered = new Date();

    private Set<Roles> roles;

    private Restaurant vote;

    public User(Integer id, String name, Restaurant vote) {
        super(id, name);
        this.vote = vote;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Restaurant getVote() {
        return vote;
    }

    public void setVote(Restaurant vote) {
        this.vote = vote;
    }

    @Override
    public String toString() {
        return "User{" +
                "vote=" + vote +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
