package edu.cmu.sda.bookreader.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.*;
import edu.cmu.sda.bookreader.repository.BookProgressRepository;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;
import edu.cmu.sda.bookreader.repository.RecommendedBookshelfRepository;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:clear_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookshelfControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private RecommendedBookshelfRepository recommendedBookshelfRepository;

    @Autowired
    private BookProgressRepository bookProgressRepository;

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
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("WantToRead");
        bookshelf.setBookshelfUser(1);
        List<String> books = new ArrayList<>();
        books.add("test-bookId");
        bookshelf.setBooks(books);

        // test
        ResponseEntity<Bookshelf> responseBookshelf = this.restTemplate.postForEntity(getRootUrl() + "/api/bookshelves", bookshelf, Bookshelf.class);
        assertEquals(bookshelf.getName(), responseBookshelf.getBody().getName());
        assertEquals(bookshelf.getBookshelfUserID(), responseBookshelf.getBody().getBookshelfUserID());
        assertEquals(bookshelf.getBooks(), responseBookshelf.getBody().getBooks());
    }

    @Test
    public void testAddRecommendedBookshelf() {
        // prepare the data
        RecommendedBookshelf bookshelf = new RecommendedBookshelf();
        bookshelf.setName("Recommended");
        bookshelf.setBookshelfUser(1);
        List<String> books = new ArrayList<>();
        books.add("test-bookId");
        bookshelf.setBooks(books);
        List<Long> users = new ArrayList<>();
        users.add(2L);
        bookshelf.setRecommenders(users);
        bookshelf.setType("recommended");

        // test
        ResponseEntity<RecommendedBookshelf> responseBookshelf = this.restTemplate.postForEntity(getRootUrl() + "/api/bookshelves", bookshelf, RecommendedBookshelf.class);
        assertEquals(bookshelf.getName(), responseBookshelf.getBody().getName());
        assertEquals(bookshelf.getBookshelfUserID(), responseBookshelf.getBody().getBookshelfUserID());
        assertEquals(bookshelf.getBooks(), responseBookshelf.getBody().getBooks());
        assertEquals(bookshelf.getRecommenders(), responseBookshelf.getBody().getRecommenders());
    }

    @Test
    public void initializeBookshelfTest() {
        // prepare data
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");

        // test
        JsonNode allBookshelves = this.restTemplate.postForObject(getRootUrl() + "/api/bookshelves/all", requestJson, JsonNode.class);
        System.out.println(allBookshelves);
        assertEquals(5, allBookshelves.size());
    }

    @Test
    public void addBookTest() {
        // prepare data
        String googleBookID = "123ndawkl12d3";
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");

        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId(googleBookID);
        book.setDescription("im a book");
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/123ndawkl12d3", Book.class);
        assertEquals("me", responseBook.getAuthor());

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfUser(1);
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.setName("Reading");
        bookshelfRepository.save(bookshelf);

        // test data
        this.restTemplate.put(getRootUrl() + "/api/bookshelves/Reading/books?userID=1&bookID=123ndawkl12d3", responseBook);
        ResponseEntity<AbstractBookshelf> response = restTemplate.getForEntity(getRootUrl() + "/api/bookshelves/Reading?userID=1", AbstractBookshelf.class);
        assertEquals(true, response.getBody().getBooks().contains(googleBookID));
    }

    @Test
    public void addBookToRecommendedTest() {
        // prepare data
        String googleBookID = "123ndawkl12d3";
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");

        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId(googleBookID);
        book.setDescription("im a book");
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/123ndawkl12d3", Book.class);
        assertEquals("me", responseBook.getAuthor());

        RecommendedBookshelf bookshelf = new RecommendedBookshelf();
        bookshelf.setBookshelfUser(1);
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.setName("Reading");
        bookshelf.setRecommenders(new ArrayList<>());
        bookshelf.getRecommenders().add(2L);
        recommendedBookshelfRepository.save(bookshelf);

        // test data
        this.restTemplate.put(getRootUrl() + "/api/bookshelves/recommended/books?userID=1&bookID=123ndawkl12d3&recommenderID=2", "");
        ResponseEntity<AbstractBookshelf> response = restTemplate.getForEntity(getRootUrl() + "/api/bookshelves/Recommended?userID=1", AbstractBookshelf.class);
        assertEquals(true, response.getBody().getBooks().contains(googleBookID));
    }

    @Test
    public void getAllBooksInBookshelfTest() {
        // prepare data
        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId("googleBookID");
        book.setDescription("im a book");
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/googleBookID", Book.class);
        assertEquals("me", responseBook.getAuthor());

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfUser(1);
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.setName("Reading");
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.getBooks().add("googleBookID");
        bookshelfRepository.save(bookshelf);

        // test data
        ResponseEntity<List> responseBooks = this.restTemplate.getForEntity(getRootUrl() + "/api/bookshelves/Reading/books?userID=1", List.class);
        LinkedHashMap books = (LinkedHashMap) responseBooks.getBody().get(0);
        assertEquals(1, responseBooks.getBody().size());
        assertEquals("Book Title", books.get("title"));
    }

    @Test
    public void getBookshelfTest() {
        // prepare data
        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId("googleBookID");
        book.setDescription("im a book");
        book.setTotalPage(100);
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/googleBookID", Book.class);
        assertEquals("me", responseBook.getAuthor());

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfUser(1);
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.setName("Reading");
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.getBooks().add("googleBookID");
        bookshelfRepository.save(bookshelf);

        // test data
        AbstractBookshelf responseBookshelf = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelves/Reading?userID=1", Bookshelf.class);
        assertEquals(bookshelf.getName(), responseBookshelf.getName());
        assertEquals(bookshelf.getBookshelfUserID(), responseBookshelf.getBookshelfUserID());
        assertEquals(true, responseBookshelf.getBooks().contains("googleBookID"));
    }

    @Test
    public void moveBookTest() {
        // prepare data
        Bookshelf bookshelfOne = new Bookshelf();
        bookshelfOne.setBookshelfUser(1);
        bookshelfOne.setBooks(new ArrayList<>());
        bookshelfOne.setName("WantToRead");
        bookshelfOne.setBooks(new ArrayList<>());
        bookshelfOne.getBooks().add("googleBookID");
        bookshelfRepository.save(bookshelfOne);

        Bookshelf bookshelfTwo = new Bookshelf();
        bookshelfTwo.setBookshelfUser(1);
        bookshelfTwo.setBooks(new ArrayList<>());
        bookshelfTwo.setName("Reading");
        bookshelfTwo.setBooks(new ArrayList<>());
        bookshelfRepository.save(bookshelfTwo);

        // test data
        restTemplate.put(getRootUrl() + "/api/bookshelves/WantToRead?userID=1&bookID=googleBookID&newBookshelf=Reading", String.class);
        AbstractBookshelf responseBookshelf = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelves/Reading?userID=1", Bookshelf.class);
        assertEquals(bookshelfTwo.getName(), responseBookshelf.getName());
        assertEquals(bookshelfTwo.getBookshelfUserID(), responseBookshelf.getBookshelfUserID());
        assertEquals(true, responseBookshelf.getBooks().contains("googleBookID"));
    }

    @Test
    public void moveBookBasedOnProgressTest() {
        // prepare data
        String googleBookID = "123ndawkl12d3";
        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userID", "1");

        Book book = new Book();
        book.setAuthor("me");
        book.setTitle("Book Title");
        book.setGoogleBookId(googleBookID);
        book.setDescription("im a book");
        book.setTotalPage(100);

        // add book to database
        restTemplate.postForEntity(getRootUrl() + "/api/book", book, Book.class);
        Book responseBook = this.restTemplate.getForObject(getRootUrl() + "/api/book/123ndawkl12d3", Book.class);
        assertEquals("me", responseBook.getAuthor());

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfUser(1);
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.setName("WantToRead");
        bookshelfRepository.save(bookshelf);

        // add book to bookshelf
        this.restTemplate.put(getRootUrl() + "/api/bookshelves/WantToRead/books?userID=1&bookID=123ndawkl12d3", responseBook);
        ResponseEntity<AbstractBookshelf> response = restTemplate.getForEntity(getRootUrl() + "/api/bookshelves/WantToRead?userID=1", AbstractBookshelf.class);
        assertEquals(true, response.getBody().getBooks().contains(googleBookID));

        Bookshelf bookshelfReading = new Bookshelf();
        bookshelfReading.setBookshelfUser(1);
        bookshelfReading.setBooks(new ArrayList<>());
        bookshelfReading.setName("Reading");
        bookshelfRepository.save(bookshelfReading);

        // update progress
        this.restTemplate.put(getRootUrl() + "/api/progress?userID=1&bookID=123ndawkl12d3&pagesFinished=100", Integer.class);

        // test
        this.restTemplate.put(getRootUrl() + "/api/bookshelves?userID=1&bookID=123ndawkl12d3", String.class);

        AbstractBookshelf responseBookshelf = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelves/Reading?userID=1", Bookshelf.class);
        assertEquals(bookshelfReading.getName(), responseBookshelf.getName());
        assertEquals(bookshelfReading.getBookshelfUserID(), responseBookshelf.getBookshelfUserID());
    }

    @Test
    public void removeBookTest() {
        // prepare data
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setBookshelfUser(1);
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.setName("Reading");
        bookshelf.setBooks(new ArrayList<>());
        bookshelf.getBooks().add("googleBookID");
        bookshelfRepository.save(bookshelf);

        this.restTemplate.put(getRootUrl() + "/api/bookshelves/Reading/remove?userID=1&bookID=googleBookID", AbstractBookshelf.class);

        AbstractBookshelf responseBookshelf = this.restTemplate.getForObject(getRootUrl() + "/api/bookshelves/Reading?userID=1", Bookshelf.class);
        assertEquals(bookshelf.getName(), responseBookshelf.getName());
        assertEquals(bookshelf.getBookshelfUserID(), responseBookshelf.getBookshelfUserID());
        assertEquals(0, responseBookshelf.getBooks().size());
    }
}
