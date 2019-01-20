package ru.lukashev.vote.web;
import ru.lukashev.vote.model.AbstractNamedEntity;


public class SecurityUtil {

    private static int id = AbstractNamedEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtil.id = id;
    }

}