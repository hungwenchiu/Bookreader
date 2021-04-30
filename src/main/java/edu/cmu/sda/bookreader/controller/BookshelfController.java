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

    @RequestMapping(value = "/bookshelves", method = RequestMethod.POST)
    public AbstractBookshelf addBookshelf(@RequestBody AbstractBookshelf bookshelf) {
        return bookshelfService.addBookshelf(bookshelf);
    }

    @RequestMapping(value = "/bookshelves/{id}/books", method = RequestMethod.POST)
    public AbstractBookshelf addBookshelf(@PathVariable("id") int id, @RequestBody Book book) {
        return bookshelfService.addBook(id, book);
    }

    @RequestMapping("/bookshelves/{id}/books/{bookid}")
    public Book getBookFromBookshelf(@PathVariable("id") int id, @PathVariable("bookid") int bookID) {
        return bookshelfService.getBookByID(id, bookID);
    }

    @RequestMapping("/bookshelves/{id}/books")
    public Set<Book> getBookFromBookshelf(@PathVariable("id") int id) {
        return bookshelfService.getAllBooksInBookshelf(id);
    }

    @RequestMapping(value="/bookshelves", method = RequestMethod.GET)
    public List<Bookshelf> getAllBookshelves() {
        return bookshelfService.getAllRegularBookshelves();
    }

    @RequestMapping(value = "/recommended", method = RequestMethod.GET)
    public List<RecommendedBookshelf> getAllRecommendBookshelves() {
        return bookshelfService.getAllRecommendedBookshelves();
    }

    @RequestMapping("/bookshelves/{id}")
    public AbstractBookshelf getAnyBookshelf(@PathVariable("id") int id) {
        return bookshelfService.getBookshelf(id);
    }

    @RequestMapping("/bookshelves/all")
    public List<AbstractBookshelf> getAll() {
        return bookshelfService.getAllAbstractBookshelf();
    }
}
