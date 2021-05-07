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

    // create all bookshelves for a user
    public List<AbstractBookshelf> initializeBookshelves(Long userID) {
       String[] bookshelfNames = new String[] {"Want To Read", "Reading", "Read", "Favorite", "Recommended"};

       List<AbstractBookshelf> bookshelves = new ArrayList<>();
       for (String bookshelfName: bookshelfNames) {
           if (!bookshelfName.equals("Recommended")) {
               if (this.getBookshelfForUserByName(userID, bookshelfName) == null) {
                   System.out.println("Initializing "+bookshelfName+" bookshelf...");
                   Bookshelf bookshelf = new Bookshelf();
                   bookshelf.setName(bookshelfName);
                   bookshelf.setBookshelfUser(userID);
                   bookshelf.setBooks(new ArrayList<>());
                   bookshelves.add(this.addBookshelf(bookshelf));
               }
           } else {
               if (this.getBookshelfForUserByName(userID, bookshelfName) == null) {
                   System.out.println("Initializing " + bookshelfName + " bookshelf...");
                   RecommendedBookshelf bookshelf = new RecommendedBookshelf();
                   bookshelf.setName(bookshelfName);
                   bookshelf.setBookshelfUser(userID);
                   bookshelf.setBooks(new ArrayList<>());
                   bookshelf.setRecommenders(new ArrayList<>());
                   bookshelves.add(this.addBookshelf(bookshelf));
               }
           }
       }
       return bookshelves;
    }

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

    // Get all bookshelves for a user
    public List<AbstractBookshelf> getAllAbstractBookshelfForUser(Long userID) {
        List<AbstractBookshelf> all = new ArrayList<>();

        for (Object bookshelf: bookshelfRepository.findByBookshelfUserID(userID)) {
            all.add((AbstractBookshelf) bookshelf);
        }
        recommendedBookshelfRepository.findByBookshelfUserID(userID).forEach(all::add);

        return all;
    }

    // Get a bookshelf by ID
    public AbstractBookshelf getBookshelf(long id, Long userID) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(id));
        Optional<RecommendedBookshelf> recommendedBookshelf = recommendedBookshelfRepository.findById(new Long(id));

        if (bookshelf.isPresent()) {
            if (userID == bookshelf.get().getBookshelfUserID()) {
                // checking user authorization
                return bookshelf.get();
            }
        }
        else if (recommendedBookshelf.isPresent()) {
            if (userID == recommendedBookshelf.get().getBookshelfUserID()) {
                return recommendedBookshelf.get();
            }
        }
        return null;
    }

    // Get bookshelf for a user by bookshelf name
    public AbstractBookshelf getBookshelfForUserByName(Long userID, String bookshelfName) {
        List<AbstractBookshelf> userBookshelves = this.getAllAbstractBookshelfForUser(userID);
        for (AbstractBookshelf userBookshelf: userBookshelves) {
            if (userBookshelf.getName().equals(bookshelfName)) {
                return userBookshelf;
            }
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
    public Book getBookByID(long bookshelfID, String bookID, Long userID) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));
        Optional<RecommendedBookshelf> recommendedBookshelf = recommendedBookshelfRepository.findById(new Long(bookshelfID));

        if (bookshelf.isPresent()) {
            if (userID == bookshelf.get().getBookshelfUserID()) {
                // check user authorization
                List<String> books = bookshelf.get().getBooks();
                if (books.contains(bookID)) {
                    Book book = bookRepository.findByGoogleBookId(bookID);
                    return book;
                }
            }
        }
        if (recommendedBookshelf.isPresent()) {
            if (userID == recommendedBookshelf.get().getBookshelfUserID()) {
                // check user authorization
                List<String> books = recommendedBookshelf.get().getBooks();
                if (books.contains(bookID)) {
                    Book book = bookRepository.findByGoogleBookId(bookID);
                    return book;
                }
            }
        }
        return null;
    }

    // Get all books from a bookshelf
    public List<Book> getAllBooksInBookshelf(long bookshelfID, Long userID) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));
        Optional<RecommendedBookshelf> recommendedBookshelf = recommendedBookshelfRepository.findById(new Long(bookshelfID));

        if (bookshelf.isPresent()) {
            if (userID == bookshelf.get().getBookshelfUserID()) {
                // checking user authorization
                List<String> bookIDs = bookshelf.get().getBooks();
                List<Book> books = new ArrayList<>();
                for (String bookID: bookIDs) {
                    Book book = bookRepository.findByGoogleBookId(bookID);
                    if (book != null) {
                        books.add(book);
                    }
                }
                return books;
            }
        }
        if (recommendedBookshelf.isPresent()) {
            if (userID == recommendedBookshelf.get().getBookshelfUserID()) {
                // check user authorization
                List<String> bookIDs = recommendedBookshelf.get().getBooks();
                List<Book> books = new ArrayList<>();
                for (String bookID: bookIDs) {
                    Book book = bookRepository.findByGoogleBookId(bookID);
                    if (book != null) {
                        books.add(book);
                    }
                }
                return books;
            }
        }
        return null;
    }

    // add a book to a bookshelf
    public AbstractBookshelf addBook(long bookshelfID, String newBookID, Long userID) {
        Optional<Bookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));
        Optional<RecommendedBookshelf> recommendedBookshelf = recommendedBookshelfRepository.findById(new Long(bookshelfID));
        if (bookshelf.isPresent()) {
            if (userID == bookshelf.get().getBookshelfUserID()) {
                // check if the user is authorized to access the bookshelf
                List<String> bookIDs = bookshelf.get().getBooks();
                if (!bookIDs.contains(newBookID)) {
                    bookIDs.add(newBookID);
                    bookshelfRepository.save(bookshelf.get());
                }
                return bookshelf.get();
            }
        }
        if (recommendedBookshelf.isPresent()) {
            List<String> bookIDs = recommendedBookshelf.get().getBooks();
            if (!bookIDs.contains(newBookID)) {
                bookIDs.add(newBookID);
                bookshelfRepository.save(bookshelf.get());
            }
            return recommendedBookshelf.get();
        }
        return null;
    }

    public AbstractBookshelf removeBook(long bookshelfID, String bookID) {
        Optional<AbstractBookshelf> bookshelf = bookshelfRepository.findById(new Long(bookshelfID));

        if (bookshelf.isPresent()) {
            List<String> bookIDs = bookshelf.get().getBooks();
            bookIDs.remove(bookID);
            bookshelfRepository.save(bookshelf.get());
            return bookshelf.get();
        }
        return null;
    }

    // move book to a bookshelf
    public String moveBook(long bookshelfID_current, long bookshelfID_new, String bookID, Long userID) {
        Optional<Bookshelf> currentBookshelf = bookshelfRepository.findById(bookshelfID_current);
        Optional<RecommendedBookshelf> currentRecommendedBookshelf = recommendedBookshelfRepository.findById(bookshelfID_current);

        if (currentBookshelf.isPresent() || currentRecommendedBookshelf.isPresent()) {
            List<String> bookIDs = new ArrayList<>();
            if (currentBookshelf.isPresent() && userID == currentBookshelf.get().getBookshelfUserID()) {
                bookIDs = currentBookshelf.get().getBooks();
            } else if (currentRecommendedBookshelf.isPresent() && userID == currentRecommendedBookshelf.get().getBookshelfUserID()){
                bookIDs = currentRecommendedBookshelf.get().getBooks();
            }

            // check if the book exists in the current bookshelf
            if (bookIDs.contains(bookID)) {
                // remove bookID from existing bookshelf
                this.removeBook(bookshelfID_current, bookID);

                // add bookID to new bookshelf
                Optional<Bookshelf> newBookshelf = bookshelfRepository.findById(bookshelfID_new);
                Optional<RecommendedBookshelf> newRecommendedBookshelf = recommendedBookshelfRepository.findById(bookshelfID_new);

                if (newBookshelf.isPresent() || newRecommendedBookshelf.isPresent()) {
                    AbstractBookshelf result = this.addBook(bookshelfID_new, bookID, userID);
                    if (result != null) {
                        return "Successfully moved book to another bookshelf";
                    }
                } else {
                    return "New bookshelf does not exist. Please try again.";
                }
            } else {
                return "Current bookshelf or the requested book does not exist. Please try again.";
            }
        }
        return "Could not move book to another bookshelf";
    }
}
