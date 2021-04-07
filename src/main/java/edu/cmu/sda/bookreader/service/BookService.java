package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "session")
@Component(value = "bookService")
public class BookService {
    @Autowired
    private BookRepository repository;

    public Book saveBook(Book book) {
        return repository.save(book);
    }

    public List<Book> saveBooks(List<Book> books) {
        return repository.saveAll(books);
    }

    public List<Book> getBooks() {
        return repository.findAll();
    }

    public Book getBookByISBN(long isbn) {
        return repository.findById(isbn).orElse(null);
    }

    public List<Book> getBookByTitle(String name) {
        return repository.findByTitle(name);
    }

    public String deleteBook(long isbn) {
        repository.deleteById(isbn);
        return "book removed - " + isbn;
    }

    public Book updateBook(Book book) {
        Book existingBook = repository.findById(book.getGoogleBookID()).orElse(null);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        return repository.save(existingBook);
    }

}
