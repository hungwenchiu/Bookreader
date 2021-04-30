package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.AbstractBookshelf;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.entity.RecommendedBookshelf;
import edu.cmu.sda.bookreader.repository.BookRepository;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;
import edu.cmu.sda.bookreader.repository.RecommendedBookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Component(value = "abstractBookService")
public class AbstractBookshelfService {
    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private RecommendedBookshelfRepository recommendedBookshelfRepository;

    @Autowired
    private BookRepository bookRepository;

    // Get all regular bookshelves (Read, WantToRead, Reading, Favorite)
    public List<Bookshelf> getAllRegularBookshelves() {
        List<Bookshelf> bookshelves = new ArrayList<>();
        for (Object bookshelf: bookshelfRepository.findAll()) {
            bookshelves.add((Bookshelf) bookshelf);
        }

        return bookshelves;
    }

    // Get all recommended bookshelves
    public List<RecommendedBookshelf> getAllRecommendedBookshelves() {
        List<RecommendedBookshelf> recommendedBookshelves = new ArrayList<>();
        recommendedBookshelfRepository.findAll().forEach(recommendedBookshelves::add);

        return recommendedBookshelves;
    }

    // Get all bookshelves
    public List<AbstractBookshelf> getAllAbstractBookshelf() {
        List<AbstractBookshelf> all = new ArrayList<>();

        for (Object bookshelf: bookshelfRepository.findAll()) {
            all.add((AbstractBookshelf) bookshelf);
        }
        recommendedBookshelfRepository.findAll().forEach(all::add);

        return all;
    }

    // Get a bookshelf by ID
    public AbstractBookshelf getBookshelf(long id) {
        System.out.println("reached bookshelf in service");
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(id));
        System.out.println(bookshelf.isPresent());
        if (bookshelf.isPresent()) {
            return bookshelf.get();
        }
        return null;
    }


    // It is working as our factory method
    public AbstractBookshelf addBookshelf(AbstractBookshelf abstractBookshelf) {
        if (abstractBookshelf instanceof Bookshelf) {
            return (AbstractBookshelf) bookshelfRepository.save((Bookshelf) abstractBookshelf);
        }
        return recommendedBookshelfRepository.save((RecommendedBookshelf) abstractBookshelf);
    }

    public void recommendBookshelf(Bookshelf bookshelf) {
        bookshelfRepository.save(bookshelf);
    }

    // Get Book from a bookshelf
    public Book getBookByID(long bookshelfID, long bookID) {
        Optional<AbstractBookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));
        if (bookshelf.isPresent()) {
            Optional<Book> book = bookRepository.findById(bookID);
            return book.get();
        }
        return null;
    }

    // Get all books from a bookshelf
    public Set<Book> getAllBooksInBookshelf(long bookshelfID) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));

        if (bookshelf.isPresent()) {
            Set<Book> books = bookshelf.get().getBooks();
            return books;
        }
        return null;
    }

    // add a book to a bookshelf
    public AbstractBookshelf addBook(long bookshelfID, Book newBook) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));

        if (bookshelf.isPresent()) {
            Set<Book> books = bookshelf.get().getBooks();
            books.add(newBook);
            return bookshelf.get();
        }
        return null;
    }

    // move book to a bookshelf
}
