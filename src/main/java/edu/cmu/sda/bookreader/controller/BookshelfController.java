package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.entity.RecommendedBookshelf;
import edu.cmu.sda.bookreader.service.AbstractBookshelfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookshelfController")
@Slf4j
public class BookshelfController {
    @Qualifier("abstractBookService")
    @Autowired
    private AbstractBookshelfService bookshelfService;

    @RequestMapping(value = "/bookshelf", method = RequestMethod.POST)
    public AbstractBookshelf addBookshelf(@RequestBody AbstractBookshelf bookshelf) {
        return bookshelfService.addBookshelf(bookshelf);
    }

    @RequestMapping(value="/bookshelf", method = RequestMethod.GET)
    public List<Bookshelf> getAllBookshelves() {
        return bookshelfService.getAllRegularBookshelves();
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    public List<RecommendedBookshelf> getAllRecommendBookshelves() {
        return bookshelfService.getAllRecommendedBookshelves();
    }


    @RequestMapping("/bookshelf/{id}")
    public AbstractBookshelf getAnyBookshelf(@PathVariable("id") int id) {
        return bookshelfService.getBookshelf(id);
    }

    @RequestMapping("/bookshelf/all")
    public List<AbstractBookshelf> getAll() {
        return bookshelfService.getAllAbstractBookshelf();
    }
}
