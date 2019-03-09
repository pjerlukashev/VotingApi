package ru.lukashev.vote.web;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;


public class SecurityUtil {

    public static LoggedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof LoggedUser) ? (LoggedUser) principal : null;
    }

    public static LoggedUser get() {
        LoggedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    private SecurityUtil() {
    }

    public static int authUserId() {
        return get().getId();
    }

}