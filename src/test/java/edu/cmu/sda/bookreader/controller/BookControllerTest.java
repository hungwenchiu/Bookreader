package edu.cmu.sda.bookreader.controller;


import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.Book;
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
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
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
    public void testFindAllBooks() {
        List<Book> allBooks = this.restTemplate.getForObject(getRootUrl() + "/api/books", List.class);
        assertTrue(allBooks.isEmpty());
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId("googleID");
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        ResponseEntity<Book> responseBook = this.restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        assertEquals("me", responseBook.getBody().getAuthor());
    }

}