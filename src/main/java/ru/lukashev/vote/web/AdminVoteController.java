package ru.lukashev.vote.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.lukashev.vote.repository.VoteRepository;
import java.time.LocalDate;

@RestController
@RequestMapping(AdminVoteController.REST_URL)
public class AdminVoteController {

    static final String REST_URL = "/rest/admin/votes";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteRepository voteRepository;

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteVotesOnDate(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE ) LocalDate date) {
        log.info("delete all votes on date {}", date);
        voteRepository.deleteAllOnDate(date);
    }
}
