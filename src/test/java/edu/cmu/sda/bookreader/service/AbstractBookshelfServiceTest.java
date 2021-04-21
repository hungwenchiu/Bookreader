package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.BookreaderApplication;
import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookreaderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractBookshelfServiceTest {
    @Autowired
    private AbstractBookshelfService bookshelfService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookservice;


//    @Test
//    public void getAllRegularBookshelvesCorrectlyTest() {
//        // create a bookshelf user
//        User user = new User();
//        user.setName("testUser");
//        user.setPassword("testpass1");
//
//        // create a book to add in bookshelf
//        Book book = new Book();
//        book.setGoogleBookId("123456789");
//        book.setTitle("Harry Potter");
//        book.setAuthor("J.K Rowling");
//        book.setKind("Fiction");
//        book.setTotalPage(0);
//
//        Set<Book> books = new HashSet<Book>();
//        books.add(book);
//
//        // create a bookshelf
//        Bookshelf bookshelf = new Bookshelf();
//        bookshelf.setName("Want to Read");
//        bookshelf.setId(123456789L);
//        bookshelf.setBookshelfUser(user);
//        bookshelf.setBooks(books);
//
//        bookshelfService.addBookshelf(bookshelf);
//
//        // test
//        List<Bookshelf> result = bookshelfService.getAllRegularBookshelves();
//        assertEquals(result.get(0), bookshelf);
//    }

    @Test
    public void getAllRegularBookshelvesCorrectlyTest() {
        // create a bookshelf user
        User user = new User();
        user.setName("testUser");
        user.setPassword("testpass1");
        userService.saveUser(user);

        // create a book to add in bookshelf
        Book book = new Book();
        book.setGoogleBookId("123456789");
        book.setTitle("Harry Potter");
        book.setAuthor("J.K Rowling");
        book.setKind("Fiction");
        book.setTotalPage(0);
        Set<Book> books = new HashSet<Book>();
        books.add(book);
        bookservice.saveBook(book);

        // create a bookshelf
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.setName("Want to Read");
        bookshelf.setId(123456789L);
        bookshelf.setBookshelfUser(user);
        bookshelf.setBooks(books);

        bookshelfService.addBookshelf(bookshelf);

        // when
        List<Bookshelf> result = bookshelfService.getAllRegularBookshelves();

        // then
        assertEquals(result.get(0),bookshelf);
    }
}
