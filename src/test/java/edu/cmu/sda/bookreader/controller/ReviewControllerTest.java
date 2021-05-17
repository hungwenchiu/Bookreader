package edu.cmu.sda.bookreader.controller;


import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.Review;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ReviewControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    // a simple sanity check test that will fail if the application context cannot start
    @Test
    public void contextLoads() {
    }

    @Test
    public void addReviewThenGetReturnCorrectReview() {
        Review review = new Review();
        review.setGoogleBookId("abc");
        review.setUserId(0);
        review.setContent("good book");
        review.setRating(5);
        restTemplate.postForEntity(getRootUrl() + "/api/review", review, Review.class);
        ResponseEntity<List> responseReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/book/abc",
                List.class);
        LinkedHashMap myReview = (LinkedHashMap) responseReviews.getBody().get(0);
        assertEquals("good book", myReview.get("content"));
    }

    @Test
    public void findReviewByGoogleBookIdReturnCorrectSize() {
        Review review1 = new Review();
        review1.setGoogleBookId("abc");
        review1.setUserId(0);
        review1.setContent("good book");
        review1.setRating(4);
        Review review2 = new Review();
        review2.setGoogleBookId("abc");
        review2.setUserId(1);
        review2.setContent("great book");
        review2.setRating(5);
        restTemplate.postForEntity(getRootUrl() + "/api/review", review1, Review.class);
        restTemplate.postForEntity(getRootUrl() + "/api/review", review2, Review.class);
        ResponseEntity<List> responseReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/book/abc",
                List.class);
        assertEquals(2, responseReviews.getBody().size());
    }

    @Test
    public void findReviewByGoogleBookIdReturnNotFoundForNonExistingId() {
        ResponseEntity<List> responseReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/book/abc",
                List.class);
        assertEquals(404, responseReviews.getStatusCodeValue());
    }

    @Test
    public void findReviewByuSERIdReturnCorrectSize() {
        Review review1 = new Review();
        review1.setGoogleBookId("abc");
        review1.setUserId(0);
        review1.setContent("good book");
        review1.setRating(4);
        Review review2 = new Review();
        review2.setGoogleBookId("bcd");
        review2.setUserId(0);
        review2.setContent("great book");
        review2.setRating(5);
        restTemplate.postForEntity(getRootUrl() + "/api/review", review1, Review.class);
        restTemplate.postForEntity(getRootUrl() + "/api/review", review2, Review.class);
        ResponseEntity<List> responseReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/user/0",
                List.class);
        assertEquals(2, responseReviews.getBody().size());
    }

    @Test
    public void findReviewByUserIdReturnNotFoundForNonExistingId() {
        ResponseEntity<List> responseReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/user/0",
                List.class);
        assertEquals(404, responseReviews.getStatusCodeValue());
    }

    @Test
    public void deleteReviewMoveItFromDB() {
        Review review = new Review();
        review.setGoogleBookId("abc");
        review.setUserId(1);
        review.setContent("good book");
        review.setRating(5);
        restTemplate.postForEntity(getRootUrl() + "/api/review", review, Review.class);
        ResponseEntity<List> existingReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/book/abc",
                List.class);
        LinkedHashMap myReview = (LinkedHashMap) existingReviews.getBody().get(0);
        restTemplate.delete("/api/review/" + myReview.get("id"));
        ResponseEntity<List> responseReviews = this.restTemplate.getForEntity(getRootUrl() + "/api/review/book/abc",
                List.class);
        assertEquals(null, responseReviews.getBody());
    }


}