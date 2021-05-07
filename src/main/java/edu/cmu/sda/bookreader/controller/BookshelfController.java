package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Book;
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
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookshelfController")
@Slf4j
public class BookshelfController {
    @Qualifier("abstractBookService")
    @Autowired
    private AbstractBookshelfService bookshelfService;

    // add a new bookshelf
    @RequestMapping(value = "/bookshelves", method = RequestMethod.POST)
    public AbstractBookshelf addBookshelf(@RequestBody AbstractBookshelf bookshelf) {
        return bookshelfService.addBookshelf(bookshelf);
    }

    // initialize all bookshelves for a user
    @RequestMapping(value = "/bookshelves/all", method = RequestMethod.POST)
    public List<AbstractBookshelf> initializeBookshelves(@RequestBody Map<String, String> json) {
        return bookshelfService.initializeBookshelves(Long.parseLong(json.get("userID")));
    }

    // add a new book to a bookshelf
    @RequestMapping(value = "/bookshelves/{id}/books", method = RequestMethod.PUT)
    public AbstractBookshelf addBook(@PathVariable("id") int id, @RequestBody Map<String, String> json) {
        Long userID;
        if (json.size() == 0) {
            // recommended bookshelf, need to check friendship of recommender
            userID = Long.valueOf(0);
        } else {
            userID = Long.parseLong(json.get("userID"));
        }
        return bookshelfService.addBook(id, json.get("bookID"), userID);
    }

    // get a particular book from a particular bookshelf
    @RequestMapping("/bookshelves/{id}/books/{bookid}")
    public Book getBookFromBookshelf(@PathVariable("id") int id, @PathVariable("bookid") String bookID, @RequestBody Map<String, String> json) {
        return bookshelfService.getBookByID(id, bookID, Long.parseLong(json.get("userID")));
    }

    // get all books in a bookshelf
    @RequestMapping("/bookshelves/{id}/books")
    public List<Book> getBooksFromBookshelf(@PathVariable("id") int id, @RequestBody Map<String, String> json) {
        return bookshelfService.getAllBooksInBookshelf(id, Long.parseLong(json.get("userID")));
    }

    // get all bookshelves for a user
    @RequestMapping("/bookshelves/all/{userID}")
    public List<AbstractBookshelf> getAllBookshelvesForUser(@PathVariable("userID") int userID) {
        return bookshelfService.getAllAbstractBookshelfForUser(new Long(userID));
    }

    // get all regular bookshelves
    @RequestMapping(value="/bookshelves/regular", method = RequestMethod.GET)
    public List<Bookshelf> getAllBookshelves() {
        return bookshelfService.getAllRegularBookshelves();
    }

    // get all recommended bookshelves
    @RequestMapping(value = "/bookshelves/recommended", method = RequestMethod.GET)
    public List<RecommendedBookshelf> getAllRecommendBookshelves() {
        return bookshelfService.getAllRecommendedBookshelves();
    }

    // get all bookshelves
    @RequestMapping("/bookshelves/all")
    public List<AbstractBookshelf> getAll() {
        return bookshelfService.getAllAbstractBookshelf();
    }

    // get a bookshelf
    @RequestMapping("/bookshelves/{id}")
    public AbstractBookshelf getAnyBookshelf(@PathVariable("id") int id, @RequestBody Map<String, String> json) {
        return bookshelfService.getBookshelf(id, Long.parseLong(json.get("userID")));
    }

    // move book from a bookshelf
    @RequestMapping(value = "/bookshelves/{id}", method = RequestMethod.PUT)
    public String moveBook(@PathVariable("id") long currentBookshelfID, @RequestBody Map<String, String> json) {
        return bookshelfService.moveBook(currentBookshelfID, Long.parseLong(json.get("newBookshelfID")), json.get("bookID"), Long.parseLong(json.get("userID")));
    }
}
