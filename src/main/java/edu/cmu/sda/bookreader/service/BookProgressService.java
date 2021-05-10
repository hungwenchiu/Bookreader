package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.BookProgress;
import edu.cmu.sda.bookreader.repository.BookProgressRepository;
import edu.cmu.sda.bookreader.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "session")
@Component(value = "bookProgressService")
public class BookProgressService {
    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private AbstractBookshelfService bookshelfService;

    /* TO DO FOR SHREYA-initialize progress for a book when user adds it in the bookshelf*/
    // initialize Progress for a book for a user
    public BookProgress initializeBookProgressForUser(Long userID, String bookID) {
        BookProgress bookProgress = new BookProgress();
        bookProgress.setGoogleBookID(bookID);
        bookProgress.setUserID(userID);
        bookProgress.setPagesFinished(0);

        bookProgressRepository.save(bookProgress);
        this.moveBook(0, userID, bookID);
        return bookProgress;
    }

    // update Progress for a book for a user
    public BookProgress updateBookProgressForUser(Long userID, String bookID, int pagesFinished) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        if (currentBookProgress != null) {
            currentBookProgress.setPagesFinished(pagesFinished);

            bookProgressRepository.save(currentBookProgress);
            this.moveBook(pagesFinished, userID, bookID);
            return currentBookProgress;
        }
        return null;
    }

    // get book progress
    public int getPagesFinished(Long userID, String bookID) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        if (currentBookProgress != null) {
            return currentBookProgress.getPagesFinished();
        }
        return -1;
    }

    // calculate book progress in percentage
    public int calculateProgress(Long userID, String bookID) {
        int totalPage = bookService.getTotalPage(bookID);

        BookProgress progress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        int pagesFinished = 0;
        if (progress != null) {
            pagesFinished = progress.getPagesFinished();
        }

        if (totalPage > 0) {
            int percentage = (int) (pagesFinished * 100 / totalPage);
            return percentage;
        }
        return 0;
    }

    // check progress and move between bookshelf
    public void moveBook(int PagesFinished, long userID, String bookID) {
        // find which bookshelf the book belongs to
        List<String> bookshelfNames = bookshelfService.getBookshelfName(userID, bookID);

        // get book progress
        BookProgress progress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        int totalPages = bookService.getTotalPage(bookID);

        // Condition1: if finishedPages is 0, then add book to WantToRead
        if (progress.getPagesFinished() == 0) {
            if (bookshelfNames == null || bookshelfNames.size() == 0) {
                // simple add the book
                bookshelfService.addBook("WantToRead", bookID, userID);
            } else if (!bookshelfNames.contains("WantToRead")) {
                // if book in regular bookshelves then move book
                if (bookshelfNames.contains("Reading")) {
                    bookshelfService.moveBook("Reading", "WantToRead", bookID, userID);
                } else if (bookshelfNames.contains("Read")) {
                    bookshelfService.moveBook("Read", "WantToRead", bookID, userID);
                }

                // if books in Recommended or Favorite bookshelf then add book instead of moving
                if (bookshelfNames.contains("Recommended") || bookshelfNames.contains("Favorite")) {
                    bookshelfService.addBook("WantToRead", bookID, userID);
                }
            }
        }

        // Condition2: if finishedPages > 0 and finishedPages < totalPages, then add book to Reading
        if (progress.getPagesFinished() > 0 && progress.getPagesFinished() < totalPages) {
            if (bookshelfNames == null || bookshelfNames.size() == 0) {
                // simply add the book
                bookshelfService.addBook("Reading", bookID, userID);
            } else if (!bookshelfNames.contains("Reading")) {
                // if book in regular bookshelves then move book
                if (bookshelfNames.contains("Read")) {
                    bookshelfService.moveBook("Read", "Reading", bookID, userID);
                } else if (bookshelfNames.contains("WantToRead")) {
                    bookshelfService.moveBook("WantToRead", "Reading", bookID, userID);
                }

                // if books in Recommended or Favorite bookshelf then add book instead of moving
                if (bookshelfNames.contains("Recommended") || bookshelfNames.contains("Favorite")) {
                    bookshelfService.addBook("Reading", bookID, userID);
                }
            }
        }

        // Condition3: if finishedPages = totalPages, then add book to Read
        if (progress.getPagesFinished() == totalPages) {
            if (bookshelfNames == null || bookshelfNames.size() == 0) {
                // simply add the book
                bookshelfService.addBook("Reading", bookID, userID);
            } else if (!bookshelfNames.contains("Read")) {
                // if book in regular bookshelves then move book
                if (bookshelfNames.contains("Reading")) {
                    bookshelfService.moveBook("Reading", "Read", bookID, userID);
                } else if (bookshelfNames.contains("WantToRead")) {
                    bookshelfService.moveBook("WantToRead", "Read", bookID, userID);
                }

                // if books in Recommended or Favorite bookshelf then add book instead of moving
                if (bookshelfNames.contains("Recommended") || bookshelfNames.contains("Favorite")) {
                    bookshelfService.addBook("Read", bookID, userID);
                }
            }
        }
    }
}
