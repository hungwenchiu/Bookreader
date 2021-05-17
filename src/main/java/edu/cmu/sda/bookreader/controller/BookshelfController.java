package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.RecommendedBookshelf;
import edu.cmu.sda.bookreader.service.AbstractBookshelfService;
import edu.cmu.sda.bookreader.service.BookProgressService;
import edu.cmu.sda.bookreader.service.SystemCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookshelfController")
@Slf4j
public class BookshelfController {
    @Qualifier("abstractBookshelfService")
    @Autowired
    private AbstractBookshelfService bookshelfService;

    @Qualifier("bookProgressService")
    @Autowired
    private BookProgressService bookProgressService;

    @Qualifier("systemCountService")
    @Autowired
    private SystemCountService systemCountService;

    /**
     * add a new bookshelf
     * @param bookshelf
     * @return AbstractBookshelf
     */
    @RequestMapping(value = "/bookshelves", method = RequestMethod.POST)
    public AbstractBookshelf addBookshelf(@RequestBody AbstractBookshelf bookshelf) {
        return bookshelfService.addBookshelf(bookshelf);
    }

    /**
     * initialize all bookshelves for a user
     * @param json
     * @return List of AbstractBookshelf
     */
    @RequestMapping(value = "/bookshelves/all", method = RequestMethod.POST)
    public List<AbstractBookshelf> initializeBookshelves(@RequestBody Map<String, String> json) {
        return bookshelfService.initializeBookshelves(Long.parseLong(json.get("userID")));
    }

    /**
     * add a new book to a bookshelf
     * @param name
     * @param bookID
     * @param userID
     * @return AbstractBookshelf
     */
    @RequestMapping(value = "/bookshelves/{name}/books", method = RequestMethod.PUT)
    public AbstractBookshelf addBook(@PathVariable("name") String name, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "userID") long userID) {
        systemCountService.updateSystemCount(bookID, name, 1);
        return bookshelfService.addBook(name, bookID, userID);
    }


    /**
     * add a new book to a recommended bookshelf
     * @param userID
     * @param recommenderID
     * @param bookID
     * @return RecommendedBookshelf
     */
    @RequestMapping(value = "/bookshelves/recommended/books", method = RequestMethod.PUT)
    public RecommendedBookshelf addRecommendedBook(@RequestParam(value = "userID") long userID, @RequestParam(value = "recommenderID") long recommenderID, @RequestParam(value = "bookID") String bookID) {
        return bookshelfService.addRecommendedBook(userID, recommenderID, bookID);
    }

    /**
     * get all books in a bookshelf
     * @param name
     * @param userID
     * @return List of Book
     */
    @RequestMapping("/bookshelves/{name}/books")
    public List<Book> getBooksFromBookshelf(@PathVariable("name") String name, @RequestParam(value = "userID") long userID) {
        return bookshelfService.getAllBooksInBookshelf(name, userID);
    }

    /**
     * get all books and status in a bookshelf
     * @param name
     * @param userID
     * @return List of a Map of Book info
     */
    @RequestMapping("/bookshelves/{name}/books/info")
    public List<Map<String, Object>> getBooksFromBookshelfWithInfo(@PathVariable("name") String name, @RequestParam(value = "userID") long userID) {
        List<Book> books = bookshelfService.getAllBooksInBookshelf(name, userID);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Book book : books) {
            Map<String, Object> bookInfo = new HashMap<>();
            bookInfo.put("book", book);
            boolean isFavorite = bookshelfService.isInBookshelf("Favorite", book.getGoogleBookId(), userID);
            boolean isReading = bookshelfService.isInBookshelf("Reading", book.getGoogleBookId(), userID);
            bookInfo.put("isFavorite", isFavorite);
            bookInfo.put("isReading", isReading);
            bookInfo.put("progress", bookProgressService.calculateProgress(userID, book.getGoogleBookId()));
            result.add(bookInfo);
        }
        return result;
    }


    /**
     * get a bookshelf
     * @param name
     * @param userID
     * @return AbstractBookshelf
     */
    @RequestMapping("/bookshelves/{name}")
    public AbstractBookshelf getAnyBookshelf(@PathVariable("name") String name, @RequestParam(value = "userID") long userID) {
        return bookshelfService.getBookshelf(name, userID);
    }

    /**
     * move book from a bookshelf
     * @param currentBookshelf
     * @param userID
     * @param bookID
     * @param newBookshelf
     * @return String
     */
    @RequestMapping(value = "/bookshelves/{name}", method = RequestMethod.PUT)
    public String moveBook(@PathVariable("name") String currentBookshelf, @RequestParam(value = "userID") long userID, @RequestParam(value = "bookID") String bookID, @RequestParam(value = "newBookshelf") String newBookshelf) {
        systemCountService.updateSystemCount(bookID, newBookshelf, 1);
        systemCountService.updateSystemCount(bookID, currentBookshelf, -1);
        return bookshelfService.moveBook(currentBookshelf, newBookshelf, bookID, userID);
    }

    /**
     * move book from bookshelf based on progress
     * @param userID
     * @param bookID
     * @return String
     */
    @RequestMapping(value = "/bookshelves", method = RequestMethod.PUT)
    public String moveBook(@RequestParam(value = "userID") long userID, @RequestParam(value = "bookID") String bookID) {
        systemCountService.updateSystemCount(bookID, "Read", 1);
        return bookshelfService.checkBookProgressToMoveBetweenBookshelves(userID, bookID);
    }

    /**
     * remove book from bookshelf
     * @param userID
     * @param bookID
     * @return AbstractBookshelf
     */
    @RequestMapping(value = "/bookshelves/{name}/remove", method = RequestMethod.PUT)
    public AbstractBookshelf deleteBook(@PathVariable("name") String bookshelfName, @RequestParam(value = "userID") long userID, @RequestParam(value = "bookID") String bookID) {
        systemCountService.updateSystemCount(bookID, bookshelfName, -1);
        if (bookshelfName.equals("Recommended")) {
            return bookshelfService.removeRecommendedBook(userID, bookID);
        }
        return bookshelfService.removeBook(userID, bookshelfName, bookID);
    }
}
