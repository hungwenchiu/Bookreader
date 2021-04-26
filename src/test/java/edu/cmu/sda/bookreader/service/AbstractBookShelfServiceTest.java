package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.*;
import edu.cmu.sda.bookreader.repository.BookRepository;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;
import edu.cmu.sda.bookreader.repository.RecommendedBookshelfRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AbstractBookShelfServiceTest {
    @InjectMocks
    AbstractBookshelfService bookshelfService;

    @Mock
    BookshelfRepository bookshelfRepository;

    @Mock
    RecommendedBookshelfRepository recommendedBookshelfRepository;

    @Mock
    BookRepository bookRepository;

    @Test
    public void getAllRegularBookshelvesTest() {
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        List<Bookshelf> bookshelves = new ArrayList<Bookshelf>();
        bookshelves.add(bookshelf);

        // mock data
        when(bookshelfRepository.findAll()).thenReturn(bookshelves);

        // test
        List<Bookshelf> getAllRegularBookshelvesResult = bookshelfService.getAllRegularBookshelves();
        assertEquals(1, getAllRegularBookshelvesResult.size());
        verify(bookshelfRepository, times(1)).findAll();
        assertEquals(getAllRegularBookshelvesResult, bookshelves);
    }

    @Test
    public void getAllRecommendedBookshelvesTest() {
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a recommended bookshelf
        RecommendedBookshelf bookshelf = new RecommendedBookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        // create recommenders
        User recommender1 = new User();
        user.setName("testRecommender1");
        user.setPassword("testpass1");
        User recommender2 = new User();
        user.setName("testRecommender2");
        user.setPassword("testpass2");

        Set<User> recommenders = new HashSet<>();
        recommenders.add(recommender1);
        recommenders.add(recommender2);
        bookshelf.setRecommenders(recommenders);

        List<RecommendedBookshelf> bookshelves = new ArrayList<RecommendedBookshelf>();
        bookshelves.add(bookshelf);

        // mock data
        when(recommendedBookshelfRepository.findAll()).thenReturn(bookshelves);

        // test
        List<RecommendedBookshelf> getAllRecommendedBookshelvesResult = bookshelfService.getAllRecommendedBookshelves();
        assertEquals(1, getAllRecommendedBookshelvesResult.size());
        verify(recommendedBookshelfRepository, times(1)).findAll();
        assertEquals(getAllRecommendedBookshelvesResult, bookshelves);
    }

    @Test
    public void getAllAbstractBookshelfTest() {
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        List<Bookshelf> bookshelves = new ArrayList<Bookshelf>();
        bookshelves.add(bookshelf);

        // create a recommended bookshelf
        RecommendedBookshelf recommendedBookshelf = new RecommendedBookshelf();
        recommendedBookshelf.setName("Want to Read");
        recommendedBookshelf.setId(123456789L);
        recommendedBookshelf.setBookshelfUser(user);
        recommendedBookshelf.setBooks(books);

        // create recommenders
        User recommender1 = new User();
        user.setName("testRecommender1");
        user.setPassword("testpass1");
        User recommender2 = new User();
        user.setName("testRecommender2");
        user.setPassword("testpass2");

        Set<User> recommenders = new HashSet<>();
        recommenders.add(recommender1);
        recommenders.add(recommender2);
        recommendedBookshelf.setRecommenders(recommenders);

        List<RecommendedBookshelf> recommendedBookshelves = new ArrayList<RecommendedBookshelf>();
        recommendedBookshelves.add(recommendedBookshelf);

        // mock data
        when(bookshelfRepository.findAll()).thenReturn(bookshelves);
        when(recommendedBookshelfRepository.findAll()).thenReturn(recommendedBookshelves);

        // test
        List<AbstractBookshelf> getAllAbstractBookshelfResult = bookshelfService.getAllAbstractBookshelf();
        assertEquals(2, getAllAbstractBookshelfResult.size());
        verify(bookshelfRepository, times(1)).findAll();
        verify(recommendedBookshelfRepository, times(1)).findAll();
        assertEquals(getAllAbstractBookshelfResult.get(0), bookshelves.get(0));
        assertEquals(getAllAbstractBookshelfResult.get(1), recommendedBookshelves.get(0));
    }

    @Test
    public void getBookshelfTest() {
        // prepare data
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        Optional<Bookshelf> bookshelves = Optional.of(bookshelf);

        // mock data
        when(bookshelfRepository.findById(bookshelf.getId())).thenReturn(bookshelves);

        // test
        AbstractBookshelf getBookshelfResult = bookshelfService.getBookshelf(bookshelf.getId());
        verify(bookshelfRepository, times(1)).findById(bookshelf.getId());
        assertEquals(getBookshelfResult, bookshelves.get());
    }

    @Test
    public void getBookshelfTestReturnsNull() {
        // prepare data
        long bookshelfID = 1234567;
        Optional<Bookshelf> bookshelves = Optional.empty();;

        // mock data
        when(bookshelfRepository.findById(bookshelfID)).thenReturn(bookshelves);

        // test
        AbstractBookshelf getBookshelfResult = bookshelfService.getBookshelf(bookshelfID);
        verify(bookshelfRepository, times(1)).findById(bookshelfID);
        assertEquals(null, getBookshelfResult);
    }

    @Test
    public void addBookshelfTest_addRegularBookshelf() {
        // prepare data
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a bookshelf
        AbstractBookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        // mock data
        when(bookshelfRepository.save(bookshelf)).thenReturn(bookshelf);

        // test
        bookshelfService.addBookshelf(bookshelf);
        verify(bookshelfRepository, times(1)).save(bookshelf);
    }

    @Test
    public void addBookshelfTest_addRecommendedBookshelf() {
        // prepare data
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a bookshelf
        RecommendedBookshelf bookshelf = new RecommendedBookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        // mock data
        when(recommendedBookshelfRepository.save(bookshelf)).thenReturn(bookshelf);

        // test
        bookshelfService.addBookshelf(bookshelf);
        verify(recommendedBookshelfRepository, times(1)).save(bookshelf);
    }

    @Test
    public void recommendBookshelfTest() {
        // prepare data
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
        Set<Book> books = new HashSet<Book>();
        books.add(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        // mock data
        when(bookshelfRepository.save(bookshelf)).thenReturn(bookshelf);

        // test
        bookshelfService.recommendBookshelf(bookshelf);
        verify(bookshelfRepository, times(1)).save(bookshelf);
    }
}
