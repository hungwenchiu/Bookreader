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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

//    @Test
//    public void testFindAllBooks() {
//        List<Book> allBooks = this.restTemplate.getForObject(getRootUrl() + "/api/books", List.class);
//        assertTrue(allBooks.isEmpty());
//    }

    @Test
    public void testAddReview() {
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

}