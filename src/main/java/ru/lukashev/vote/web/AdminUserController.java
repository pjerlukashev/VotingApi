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
import java.util.List;

@RestController
@RequestMapping(AdminUserController.REST_URL)
public class AdminUserController {
    static final String REST_URL = "/rest/admin/users";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        log.info("get all users");
        return repository.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@RequestBody UserTo userTo) {
        log.info("create user {}", userTo);
        User user = UserUtil.createNewFromTo(userTo);
        ValidationUtil.checkNew(user);
        User created = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete user {}", id);
        repository.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @PathVariable("id") int id) {
        log.info("update user {} with id={}", userTo, id);
        User user = UserUtil.updateFromTo(repository.get(userTo.getId()), userTo);
        ValidationUtil.assureIdConsistent(user, id);
        repository.save(user);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByMail(@RequestParam("email") String email) {
        log.info("get user by e-mail {}", email);
        return repository.getByEmail(email);
    }

    @PutMapping(value = "/{id}/enabled")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void setEnabled( @PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
        log.info(enabled ? "enable user {}" : "disable user {}", id);
        User user = repository.get(id);
        user.setEnabled(enabled);
        repository.save(user);
    }
}