package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookController")
public class BookController {

    @Qualifier("bookService")
    @Autowired
    private BookService service;

    @PostMapping("/book")
    public Book addBook(@RequestBody Book book) {
        return service.saveBook(book);
    }

    @PostMapping("/books")
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return service.saveBooks(books);
    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return service.getBooks();
    }

    @GetMapping("/book/{isbn}")
    public Book findBookByGoogleBookID(@PathVariable long isbn) {
        return service.getBookByGoogleBookID(isbn);
    }

    @GetMapping("/books/{name}")
    public List<Book> findBookByTitle(@PathVariable String name) {
        return service.getBookByTitle(name);
    }

    @PutMapping("/book")
    public Book updateBook(@RequestBody Book book) {
        return service.updateBook(book);
    }

    @DeleteMapping("/book/{isbn}")
    public String deleteBook(@RequestBody long isbn) {
        return service.deleteBook(isbn);
    }
}
