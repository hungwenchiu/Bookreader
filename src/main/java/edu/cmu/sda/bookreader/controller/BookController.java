package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.service.BookService;
import jdk.nashorn.internal.objects.NativeJSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookController")
@Slf4j
public class BookController {

    @Qualifier("bookService")
    @Autowired
    private BookService service;

    @PostMapping("/book")
    public Book addBook(@RequestBody Book book) {

        Book exist_book = service.getBookByGoogleBookId(String.valueOf(book.getGoogleBookId()));

        // insert if there are not duplicated records
        if(exist_book == null) {
            // trim the length of the description if it excesses the limitation of the table
            if(book.getDescription().length() > 2000)
                book.setDescription(book.getDescription().substring(0, 1987) + "...Read more");

            return service.saveBook(book);
        } else {
            System.out.println("exist_book: " + exist_book.getDescription().length());
            return null;
        }

    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return service.getBooks();
    }

    @GetMapping("/book/{googleBookId}")
    public ResponseEntity<Book> findBookByGoogleBookID(@PathVariable String googleBookId) {
        Book book = service.getBookByGoogleBookId(googleBookId);

        if (null == book) {
            // log.error("Book with google book id " + googleBookId + " does not exist.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    /**
     * update book with the given book object
     * @param book
     * @return updated book object, return null if the given book's id doesn't exist
     */
    @PutMapping("/book")
    public Book updateBook(@RequestBody Book book) {
        return service.updateBook(book);
    }

    /**
     * delete book by id
     * @param id
     * @return
     */
    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable long id) {
        return service.deleteBook(id);
    }
}
