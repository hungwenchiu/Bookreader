package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;
import edu.cmu.sda.bookreader.repository.RecommendedBookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "session")
@Component(value = "bookService")
public class BookshelfService {
    private BookshelfFactory bookshelfFactory;

    public Bookshelf saveBookshelf(Bookshelf bookshelf) {
        bookshelfFactory = new BookshelfFactory();
        return bookshelfFactory.getBookshelfRepository(bookshelf.getBookshelfType()).save(bookshelf);
    }

    public List<Book> getBooks() {
        bookshelfFactory = new BookshelfFactory();
        return bookshelfFactory.getBookshelfRepository().findAll();
    }

    public Book getBookByGoogleBookId (String googleBookID) {
        return repository.findByGoogleBookId(googleBookID);
    }

    public List<Book> getBookByTitle(String name) {
        return repository.findByTitle(name);
    }

    public String deleteBook(long id) {
        repository.deleteById(id);
        return "book removed - " + id;
    }

    /**
     * update book with given book object
     * @param book
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
}
