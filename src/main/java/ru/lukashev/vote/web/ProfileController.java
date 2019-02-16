package ru.lukashev.vote.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lukashev.vote.model.User;
import ru.lukashev.vote.repository.UserRepository;
import ru.lukashev.vote.to.UserTo;
import ru.lukashev.vote.util.UserUtil;
import ru.lukashev.vote.util.ValidationUtil;
import java.net.URI;

import static ru.lukashev.vote.util.UserUtil.updateFromTo;
import static ru.lukashev.vote.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(ProfileController.REST_URL)
public class ProfileController {
    static final String REST_URL = "/rest/profile";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        log.info("getting authorized user {}", SecurityUtil.authUserId());
        return repository.get(SecurityUtil.authUserId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("deleting authorized user {}", SecurityUtil.authUserId());
        repository.delete(SecurityUtil.authUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody UserTo userTo) {
        log.info("create user {}", userTo);
        User user = UserUtil.createNewFromTo(userTo);
        checkNew(user);
        User created = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        log.info("update user {} with id={}", userTo, SecurityUtil.authUserId());
        User user = updateFromTo(repository.get(userTo.getId()), userTo);
        ValidationUtil.assureIdConsistent(user, SecurityUtil.authUserId());
        repository.save(user);
    }

    public void login(UserTo userTo) {//написать метод
    }
}