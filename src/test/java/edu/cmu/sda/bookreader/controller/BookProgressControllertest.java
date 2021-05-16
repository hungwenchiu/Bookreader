package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.BookProgress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookProgressControllertest {
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
    public void initializeBookProgressTest() {
        // prepare data
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");
        requestJson.put("bookID", "googleBookID");

        // test
        BookProgress responseProgress = this.restTemplate.postForObject(getRootUrl() + "/api/progress", requestJson, BookProgress.class);
        assertEquals("googleBookID", responseProgress.getGoogleBookID());
        assertEquals(0, responseProgress.getPagesFinished());
    }

    @Test
    public void updateProgressTest() {
        // prepare data
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");
        requestJson.put("bookID", "googleBookID");

        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId("googleBookID");
        book.setDescription("im a book");
        book.setTotalPage(100);

        // add book to database
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/googleBookID", Book.class);
        assertEquals("me", responseBook.getAuthor());

        // initialize progress
        BookProgress responseProgress = this.restTemplate.postForObject(getRootUrl() + "/api/progress", requestJson, BookProgress.class);
        assertEquals("googleBookID", responseProgress.getGoogleBookID());
        assertEquals(0, responseProgress.getPagesFinished());

        // test
        this.restTemplate.put(getRootUrl() + "/api/progress?userID=1&bookID=googleBookID&pagesFinished=100", Integer.class);

        Integer percentage = this.restTemplate.getForObject(getRootUrl() + "/api/progress?userID=1&bookID=googleBookID", Integer.class);
        assertTrue(percentage == 100);
    }

    @Test
    public void getProgressTest() {
        // prepare data
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");
        requestJson.put("bookID", "googleBookID");

        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId("googleBookID");
        book.setDescription("im a book");
        book.setTotalPage(100);

        // add book to database
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/googleBookID", Book.class);
        assertEquals("me", responseBook.getAuthor());

        // initialize progress
        BookProgress responseProgress = this.restTemplate.postForObject(getRootUrl() + "/api/progress", requestJson, BookProgress.class);
        assertEquals("googleBookID", responseProgress.getGoogleBookID());
        assertEquals(0, responseProgress.getPagesFinished());

        // test
        Integer percentage = this.restTemplate.getForObject(getRootUrl() + "/api/progress?userID=1&bookID=googleBookID", Integer.class);
        assertTrue(percentage == 0);
    }
}
