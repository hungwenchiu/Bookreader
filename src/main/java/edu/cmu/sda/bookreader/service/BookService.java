package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Scope(value = "session")
@Component(value = "bookService")
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getBookByGoogleBookId(String googleBookID) {
        return repository.findByGoogleBookId(googleBookID);
    }

    public String deleteBook(long id) {
        repository.deleteById(id);
        return "book removed - " + id;
    }

    /**
     * update book with given book object
     * @param book book to be updated
     * @return null if the given book's id doesn't exist, else return the updated book
     */
    public Book updateBook(Book book) {
        Book existingBook = repository.findById(book.getId()).orElse(null);
        if (null == existingBook) {
            return null;
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setKind(book.getKind());
        existingBook.setTotalPage(book.getTotalPage());
        return repository.save(existingBook);
    }

    public int getTotalPage(String googleBookID) {
        Book book = this.getBookByGoogleBookId(googleBookID);
        if (book == null) {
            return 0;
        }
        return book.getTotalPage();
    }

}
