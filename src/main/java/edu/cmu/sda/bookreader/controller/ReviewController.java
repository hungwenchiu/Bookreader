package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.service.BookService;
import edu.cmu.sda.bookreader.entity.Review;
import edu.cmu.sda.bookreader.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@Scope(value = "session")
@Component(value = "bookController")
@Slf4j
public class ReviewController {

    @Qualifier("reviewService")
    @Autowired
    private ReviewService service;

    @PostMapping("/review")
    public Book addReview(@RequestBody Review review) {
        return service.saveReview(review);
    }

    @GetMapping("/review/{googleBookId}")
    public ResponseEntity<Book> findBookByGoogleBookID(@PathVariable String googleBookId) {
        Book book = service.getBookByGoogleBookId(googleBookId);

        if (null == book) {
            log.error("Book with google book id " + googleBookId + " does not exist.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
    @GetMapping("/review/{userId}")
    public ResponseEntity<Book> findBookByGoogleBookID(@PathVariable String googleBookId) {
        Book book = service.getBookByGoogleBookId(googleBookId);

        if (null == book) {
            log.error("Book with google book id " + googleBookId + " does not exist.");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
    /**
     * update book with the given book object
     * @param book
     * @return updated book object, return null if the given book's id doesn't exist
     */

    /**
     * delete book by id
     * @param id
     * @return
     */
    @DeleteMapping("/book/{id}")
    public String deleteBook(@RequestBody long id) {
        return service.deleteBook(id);
    }
}
