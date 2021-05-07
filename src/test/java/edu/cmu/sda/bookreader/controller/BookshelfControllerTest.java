package edu.cmu.sda.bookreader.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookshelfControllerTest {
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
    public void testAddBookshelf() {
        // prepare the data
        // create a bookshelf user
        User user = new User();
        user.setName("testUser");
        user.setPassword("testpass1");

        // create a book to add in bookshelf
        Book book = new Book();
        book.setGoogleBookId("123456789");
        book.setTitle("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setKind("Fiction");
        book.setTotalPage(0);
        Set<Book> books = new HashSet<>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);
        bookshelf.setType("regular");

        // test
        ResponseEntity<Bookshelf> responseBookshelf = this.restTemplate.postForEntity(getRootUrl() + "/api/bookshelf", bookshelf, Bookshelf.class);
        assertEquals(bookshelf.getName(), responseBookshelf.getBody().getName());
        assertEquals(responseBookshelf.getBody().getBookshelfUser().getName(), bookshelf.getBookshelfUser().getName());
        assertEquals(responseBookshelf.getBody().getBookshelfUser().getPassword(), bookshelf.getBookshelfUser().getPassword());
        assertEquals(bookshelf.getBooks().size(), responseBookshelf.getBody().getBooks().size());
        for (Book responseBook : responseBookshelf.getBody().getBooks()) {
            assertEquals(book.getTitle(), responseBook.getTitle());
            assertEquals(book.getAuthor(), responseBook.getAuthor());
            assertEquals(book.getKind(), responseBook.getKind());
            assertEquals(book.getTotalPage(), responseBook.getTotalPage());
        }
    }

    @Test
    public void getAllBookshelvesTest() {
        // prepare the data
        // create a bookshelf user
        User user = new User();
        user.setName("testUser");
        user.setPassword("testpass1");

        // create a book to add in bookshelf
        Book book = new Book();
        book.setGoogleBookId("123456789");
        book.setTitle("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setKind("Fiction");
        book.setTotalPage(0);
        Set<Book> books = new HashSet<>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);
        bookshelf.setType("regular");

        // add a bookshelf
        this.restTemplate.postForEntity(getRootUrl() + "/api/bookshelf", bookshelf, Bookshelf.class);

        // test
        JsonNode allBookshelves = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelf", JsonNode.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Bookshelf> bookshelvesList = mapper.convertValue(
                allBookshelves,
                new TypeReference<List<Bookshelf>>() {
                }
        );

        assertEquals(bookshelf.getName(), bookshelvesList.get(0).getName());
    }

    @Test
    public void getAllRecommendBookshelvesTest() {
        List<Bookshelf> allBookshelves = this.restTemplate.getForObject(getRootUrl() + "/api/recommended", List.class);
        assertTrue(allBookshelves.isEmpty());
    }

    @Test
    public void getAllTest() {
        // prepare the data
        // create a bookshelf user
        User user = new User();
        user.setName("testUser");
        user.setPassword("testpass1");

        // create a book to add in bookshelf
        Book book = new Book();
        book.setGoogleBookId("123456789");
        book.setTitle("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setKind("Fiction");
        book.setTotalPage(0);
        Set<Book> books = new HashSet<>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);
        bookshelf.setType("regular");

        // add a bookshelf
        this.restTemplate.postForEntity(getRootUrl() + "/api/bookshelf", bookshelf, Bookshelf.class);

        // test
        JsonNode allBookshelves = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelf/all", JsonNode.class);
        ObjectMapper mapper = new ObjectMapper();
        List<AbstractBookshelf> bookshelvesList = mapper.convertValue(
                allBookshelves,
                new TypeReference<List<AbstractBookshelf>>() {
                }
        );

        assertEquals(bookshelf.getName(), bookshelvesList.get(0).getName());
    }

    @Test
    public void getAnyBookshelftest() {
        // prepare the data
        // create a bookshelf user
        User user = new User();
        user.setName("testUser");
        user.setPassword("testpass1");

        // create a book to add in bookshelf
        Book book = new Book();
        book.setGoogleBookId("123456789");
        book.setTitle("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setKind("Fiction");
        book.setTotalPage(0);
        Set<Book> books = new HashSet<>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);
        bookshelf.setType("regular");

        // add a bookshelf
        ResponseEntity<Bookshelf> responseBookshelf = this.restTemplate.postForEntity(getRootUrl() + "/api/bookshelf", bookshelf, Bookshelf.class);
        long booksheflID = responseBookshelf.getBody().getId();

        // test
        AbstractBookshelf responseAnyBookshelf = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelf/" + booksheflID, Bookshelf.class);
        System.out.println("printing final response "+responseAnyBookshelf);
        assertEquals(bookshelf.getName(), responseAnyBookshelf.getName());
        assertEquals(responseBookshelf.getBody().getId(), responseAnyBookshelf.getId());
        assertEquals(bookshelf.getBookshelfUser().getName(), responseAnyBookshelf.getBookshelfUser().getName());
        assertEquals(bookshelf.getBookshelfUser().getPassword(), responseAnyBookshelf.getBookshelfUser().getPassword());
        assertEquals(bookshelf.getBooks().size(), responseAnyBookshelf.getBooks().size());

        for (Book responseBook : responseAnyBookshelf.getBooks()) {
            assertEquals(book.getTitle(), responseBook.getTitle());
            assertEquals(book.getAuthor(), responseBook.getAuthor());
            assertEquals(book.getKind(), responseBook.getKind());
            assertEquals(book.getTotalPage(), responseBook.getTotalPage());
        }
    }
}
