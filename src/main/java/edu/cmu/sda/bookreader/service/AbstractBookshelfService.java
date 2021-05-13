package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.*;
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

@Service
@Scope(value = "session")
@Component(value = "abstractBookshelfService")
public class AbstractBookshelfService {
    @Autowired
    private BookshelfRepository bookshelfRepository;

    @Autowired
    private RecommendedBookshelfRepository recommendedBookshelfRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookProgressService progressService;

    // create all bookshelves for a user
    public List<AbstractBookshelf> initializeBookshelves(Long userID) {
        String[] bookshelfNames = new String[] {"WantToRead", "Reading", "Read", "Favorite", "Recommended"};

        List<AbstractBookshelf> bookshelves = new ArrayList<>();
        for (String bookshelfName: bookshelfNames) {
            if (!bookshelfName.equals("Recommended")) {
                Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(bookshelfName, userID);
                if (bookshelf == null) {
                    System.out.println("Initializing "+bookshelfName+" bookshelf...");
                    bookshelf = new Bookshelf();
                    bookshelf.setName(bookshelfName);
                    bookshelf.setBookshelfUser(userID);
                    bookshelf.setBooks(new ArrayList<>());
                    bookshelves.add(this.addBookshelf(bookshelf));
                }
            } else {
                RecommendedBookshelf bookshelf = recommendedBookshelfRepository.findByBookshelfUserID(userID);
                if (bookshelf == null) {
                    System.out.println("Initializing " + bookshelfName + " bookshelf...");
                    bookshelf = new RecommendedBookshelf();
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

    // It is working as our factory method
    public AbstractBookshelf addBookshelf(AbstractBookshelf abstractBookshelf) {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(abstractBookshelf.getName(), abstractBookshelf.getBookshelfUserID());
        RecommendedBookshelf recommendedBookshelf = recommendedBookshelfRepository.findByBookshelfUserID(abstractBookshelf.getBookshelfUserID());

        if (bookshelf != null && bookshelf.getName().equals(abstractBookshelf.getName())) {
            return bookshelf;
        }
        if (recommendedBookshelf != null && recommendedBookshelf.getName().equals(abstractBookshelf.getName())) {
            return recommendedBookshelf;
        }
        if (abstractBookshelf instanceof Bookshelf) {
            return (AbstractBookshelf) bookshelfRepository.save((Bookshelf) abstractBookshelf);
        }
        return recommendedBookshelfRepository.save((RecommendedBookshelf) abstractBookshelf);
    }

    public void recommendBookshelf(Bookshelf bookshelf) {
        bookshelfRepository.save(bookshelf);
    }

    // Get all bookshelves for a user
    public List<AbstractBookshelf> getAllAbstractBookshelfForUser(Long userID) {
        List<AbstractBookshelf> all = new ArrayList<>();

        for (Object bookshelf: bookshelfRepository.findByBookshelfUserID(userID)) {
            all.add((AbstractBookshelf) bookshelf);
        }
        all.add(recommendedBookshelfRepository.findByBookshelfUserID(userID));

        return all;
    }

    public Book getBookByID(String name, String bookID, Long userID) {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(name, userID);
        RecommendedBookshelf recommendedBookshelf = recommendedBookshelfRepository.findByBookshelfUserID(userID);

        if (bookshelf != null) {
            List<String> books = bookshelf.getBooks();
            if (books.contains(bookID)) {
                Book book = bookRepository.findByGoogleBookId(bookID);
                return book;
            }
        }
        if (recommendedBookshelf != null) {
            List<String> books = recommendedBookshelf.getBooks();
            if (books.contains(bookID)) {
                Book book = bookRepository.findByGoogleBookId(bookID);
                return book;
            }
        }
        return null;
    }

    // Get all books from a bookshelf
    public List<Book> getAllBooksInBookshelf(String name, Long userID) {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(name, userID);
        RecommendedBookshelf recommendedBookshelf = recommendedBookshelfRepository.findByBookshelfUserID(userID);
        if (bookshelf != null) {
            List<String> bookIDs = bookshelf.getBooks();
            List<Book> books = new ArrayList<>();
            for (String bookID: bookIDs) {
                Book book = bookRepository.findByGoogleBookId(bookID);
                if (book != null) {
                    books.add(book);
                }
            }
            return books;
        }
        if (recommendedBookshelf != null) {
                List<String> bookIDs = recommendedBookshelf.getBooks();
                List<Book> books = new ArrayList<>();
                for (String bookID: bookIDs) {
                    Book book = bookRepository.findByGoogleBookId(bookID);
                    if (book != null) {
                        books.add(book);
                    }
                }
                return books;
        }
        return null;
    }

    // add a book to a regular bookshelf
    public Bookshelf addBook(String name, String newBookID, Long userID) {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(name, userID);
        if (bookshelf != null) {
            List<String> books = bookshelf.getBooks();
            if (!books.contains(newBookID)) {
                books.add(newBookID);
                bookshelfRepository.save(bookshelf);
            }
        }
        if (name.equals("WantToRead")) {
            progressService.initializeBookProgressForUser(userID, newBookID);
        }
        return bookshelf;
    }

    // add a book to recommended bookshelf
    public RecommendedBookshelf addRecommendedBook(Long userID, Long recommenderID, String bookID) {
        RecommendedBookshelf bookshelf = recommendedBookshelfRepository.findByBookshelfUserID(userID);
        if (bookshelf != null) {
            // check friendship
            List<Long> recommenders = bookshelf.getRecommenders();
            if (!recommenders.contains(recommenderID)) {
                recommenders.add(recommenderID);
            }

            List<String> bookIDs = bookshelf.getBooks();
            if (!bookIDs.contains(bookID)) {
                bookIDs.add(bookID);
            }
            recommendedBookshelfRepository.save(bookshelf);
        }
        return bookshelf;
    }


    public Bookshelf removeBook(long userID, String name, String bookID) {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(name, userID);
        if (bookshelf != null) {
            List<String> books = bookshelf.getBooks();
            if (books.contains(bookID)) {
                books.remove(bookID);
                bookshelfRepository.save(bookshelf);
            }
        }
        return bookshelf;
    }

    public RecommendedBookshelf removeRecommendedBook(long userID, String bookID) {
        RecommendedBookshelf bookshelf = recommendedBookshelfRepository.findByBookshelfUserID(userID);
        if (bookshelf != null) {
            List<String> books = bookshelf.getBooks();
            if (books.contains(bookID)) {
                books.remove(bookID);
                recommendedBookshelfRepository.save(bookshelf);
            }
        }
        return bookshelf;
    }

    public String checkBookProgressToMoveBetweenBookshelves(long userID, String bookID) {
        // find which bookshelf the book belongs to
        List<String> bookshelfNames = this.getBookshelfName(userID, bookID);

        // get book progress
        int pagesFinished = progressService.getPagesFinished(userID, bookID);
        int totalPages = bookService.getTotalPage(bookID);
        
        // if finishedPages = totalPages, then add book to Read
        if (pagesFinished >= totalPages) {
            if (bookshelfNames == null || bookshelfNames.size() == 0) {
                // simply add the book
                this.addBook("Reading", bookID, userID);
                return "Added to Reading.";
            } else if (!bookshelfNames.contains("Read")) {
                // if book in regular bookshelves then move book
                if (bookshelfNames.contains("Reading")) {
                    this.moveBook("Reading", "Read", bookID, userID);
                } else if (bookshelfNames.contains("WantToRead")) {
                    this.moveBook("WantToRead", "Read", bookID, userID);
                }

                // if books in Recommended or Favorite bookshelf then add book instead of moving
                if (bookshelfNames.contains("Recommended") || bookshelfNames.contains("Favorite")) {
                    this.addBook("Read", bookID, userID);
                }
                return "Successfully moved the book.";
            }
        }

        return "Could not move the book successfully.";
    }

    // move book to another regular bookshelf
    public String moveBook(String bookshelf_current, String bookshelf_new, String bookID, Long userID) {
        Bookshelf currentBookshelf = bookshelfRepository.findBookshelfByNameForUser(bookshelf_current, userID);

        if (currentBookshelf != null) {
            List<String> books = currentBookshelf.getBooks();
            // check if the book exists in the current bookshelf
            if (books.contains(bookID)) {
                // remove bookID from existing bookshelf
                this.removeBook(userID, currentBookshelf.getName(), bookID);

                // add bookID to new bookshelf
                Bookshelf newBookshelf = bookshelfRepository.findBookshelfByNameForUser(bookshelf_new, userID);
                if (newBookshelf != null) {
                    Bookshelf result = addBook(newBookshelf.getName(), bookID, userID);

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

    // get the bookshelf name in which a particular book exists, given a user
    public List<String> getBookshelfName(long userID, String bookID) {
        List<Bookshelf> bookshelves = bookshelfRepository.findByBookshelfUserID(userID);
        List<String> bookshelfNames = new ArrayList<>();
        for (Bookshelf bookshelf: bookshelves) {
            if (bookshelf.getBooks().contains(bookID)) {
                bookshelfNames.add(bookshelf.getName());
            }
        }
        return bookshelfNames;
    }


    /**
     * check if the given book is in the bookshelf with bookshelfName for user with userId
     * @param bookshelfName
     * @param bookId
     * @param userId
     * @return
     */
    public boolean isInBookshelf(String bookshelfName, String bookId, long userId) {
        Bookshelf bookshelf = bookshelfRepository.findBookshelfByNameForUser(bookshelfName, userId);
        return bookshelf.getBooks().contains(bookId);
    }
}
