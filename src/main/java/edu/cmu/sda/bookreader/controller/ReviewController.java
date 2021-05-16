package edu.cmu.sda.bookreader.controller;

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
@Component(value = "reviewController")
@Slf4j
public class ReviewController {

    @Qualifier("reviewService")
    @Autowired
    private ReviewService service;

    /**
     * post a review and add it to the database
     * @param review: A review Object
     * @return saved review
     */
    @PostMapping("/review")
    public Review addReview(@RequestBody Review review) {
        return service.saveReview(review);
    }

    /**
     * Get all the reviews for the book with googleBookId
     * @param googleBookId: google Book ID of the book to get reviews from
     * @return A list of reviews
     */
    @GetMapping("/review/book/{googleBookId}")
    public ResponseEntity<List<Review>> findReviewsByGoogleBookID(@PathVariable String googleBookId) {
        List<Review> reviews = service.getReviewsByGoogleBookId(googleBookId);
        if (reviews == null || reviews.isEmpty()) {
            log.error("No reviews are available with google book id " + googleBookId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    /**
     * Get all the reviews of a User
     * @param userId: userId of the User to get the reviews from
     * @return A list of reviews
     */
    @GetMapping("/review/user/{userId}")
    public ResponseEntity<List<Review>> findReviewsByUserID(@PathVariable long userId) {
        List<Review> reviews = service.getReviewsByUserId(userId);
        if (reviews == null || reviews.isEmpty()) {
            log.error("No reviews are available with user id " + userId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviews);
    }

    /**
     * delete review by id
     * @param id: the id of review to delete
     * @return A string indicating the review is deleted
     */
    @DeleteMapping("/review/{id}")
    public String deleteReview(@PathVariable long id) {
        return service.deleteReview(id);
    }
}
