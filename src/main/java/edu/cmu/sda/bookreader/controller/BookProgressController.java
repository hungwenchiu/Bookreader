package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.BookProgress;
import edu.cmu.sda.bookreader.service.BookProgressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookProgressController")
@Slf4j
public class BookProgressController {
    @Qualifier("bookProgressService")
    @Autowired
    private BookProgressService bookProgressService;

    // initialize book progress
    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public BookProgress initializeProgress(@RequestBody Map<String, String> json) {
        return bookProgressService.initializeBookProgressForUser(Long.parseLong(json.get("userID")), json.get("bookID"));
    }

    // update book progress
    @RequestMapping(value = "/progress", method = RequestMethod.PUT)
    public BookProgress updateProgress(@RequestBody Map<String, String> json) {
        return bookProgressService.updateBookProgressForUser(Long.parseLong(json.get("userID")), json.get("bookID"), Long.parseLong(json.get("pagesFinished")));
    }
}