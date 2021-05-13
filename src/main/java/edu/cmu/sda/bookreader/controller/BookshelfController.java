package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
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
    @RequestMapping(value = "/bookshelves/{name}/books", method = RequestMethod.PUT)
    public AbstractBookshelf addBook(@PathVariable("name") String name, @RequestParam(value="bookID") String bookID, @RequestParam(value="userID") long userID) {
        systemCountService.updateSystemCount(bookID, name, 1);
        return bookshelfService.addBook(name, bookID, userID);
    }


    // add a new book to a recommended bookshelf
    @RequestMapping(value = "/bookshelves/recommended/books", method = RequestMethod.PUT)
    public RecommendedBookshelf addRecommendedBook(@RequestParam(value="userID") long userID, @RequestParam(value="recommenderID") long recommenderID, @RequestParam(value="bookID") String bookID) {
        return bookshelfService.addRecommendedBook(userID, recommenderID, bookID);
    }

    // get a particular book from a particular bookshelf
    @RequestMapping("/bookshelves/{name}/books/{bookid}")
    public Book getBookFromBookshelf(@PathVariable("name") String name, @PathVariable("bookid") String bookid, @RequestBody Map<String, String> json) {
        return bookshelfService.getBookByID(name, bookid, Long.parseLong(json.get("userID")));
    }

    // get all books in a bookshelf
    @RequestMapping("/bookshelves/{name}/books")
    public List<Book> getBooksFromBookshelf(@PathVariable("name") String name, @RequestParam(value="userID") long userID) {
        return bookshelfService.getAllBooksInBookshelf(name, userID);
    }

    // get all books and status in a bookshelf
    @RequestMapping("/bookshelves/{name}/books/info")
    public List<Map<String, Object>> getBooksFromBookshelfWithInfo(@PathVariable("name") String name, @RequestParam(value="userID") long userID) {
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
    @RequestMapping(value = "/bookshelves/{name}", method = RequestMethod.PUT)
    public String moveBook(@PathVariable("name") String currentBookshelf,@RequestParam(value="userID") long userID, @RequestParam(value="bookID") String bookID, @RequestParam(value="newBookshelf") String newBookshelf) {
        systemCountService.updateSystemCount(bookID, newBookshelf, 1);
        systemCountService.updateSystemCount(bookID, currentBookshelf, -1);
        return bookshelfService.moveBook(currentBookshelf, newBookshelf, bookID, userID);
    }

    /**
     * move book from bookshelf based on progress
     * @param userID
     * @param bookID
     * @return
     */
    @RequestMapping(value = "/bookshelves", method = RequestMethod.PUT)
    public String moveBook(@RequestParam(value="userID") long userID, @RequestParam(value="bookID") String bookID) {
        systemCountService.updateSystemCount(bookID, "Read", 1);
        return bookshelfService.checkBookProgressToMoveBetweenBookshelves(userID, bookID);
    }

    /**
     * remove book from bookshelf
     * @param userID
     * @param bookID
     * @return
     */
    @RequestMapping(value = "/bookshelves/{name}/remove", method = RequestMethod.PUT)
    public AbstractBookshelf deleteBook(@PathVariable("name") String bookshelfName,@RequestParam(value="userID") long userID, @RequestParam(value="bookID") String bookID) {
        systemCountService.updateSystemCount(bookID, bookshelfName, -1);
        if (bookshelfName.equals("Recommended")) {
            return bookshelfService.removeRecommendedBook(userID, bookID);
        }
        return bookshelfService.removeBook(userID, bookshelfName, bookID);
    }
}
