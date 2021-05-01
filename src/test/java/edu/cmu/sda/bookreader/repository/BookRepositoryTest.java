package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void canInsert() {
        final String title = "This is a book name";
        final String author = "me";
        final String googleBookId = "bookId";
        final int totalPageNum = 100;
        Book book = new Book();
        book.setGoogleBookId(googleBookId);
        book.setAuthor(author);
        book.setTotalPage(totalPageNum);
        book.setTitle(title);
        bookRepository.save(book);
        Book bookInDb = bookRepository.findByGoogleBookId(googleBookId);
        assertNotNull(bookInDb);
        assertEquals(title, bookInDb.getTitle());
    }
}
