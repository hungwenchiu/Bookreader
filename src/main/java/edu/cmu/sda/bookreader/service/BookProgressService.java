package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.BookProgress;
import edu.cmu.sda.bookreader.repository.BookProgressRepository;
import edu.cmu.sda.bookreader.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "bookProgressService")
public class BookProgressService {
    @Autowired
    BookProgressRepository bookProgressRepository;

    @Autowired
    private BookRepository bookRepository;

    /* TO DO FOR SHREYA-initialize progress for a book when user adds it in the bookshelf*/
    // initialize Progress for a book for a user
    public BookProgress initializeBookProgressForUser(Long userID, String bookID) {
        BookProgress bookProgress = new BookProgress();
        bookProgress.setGoogleBookID(bookID);
        bookProgress.setUserID(userID);
        bookProgress.setPagesFinished(0);

        return bookProgressRepository.save(bookProgress);
    }

    // update Progress for a book for a user
    public BookProgress updateBookProgressForUser(Long userID, String bookID, int pagesFinished) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        if (currentBookProgress != null) {
            currentBookProgress.setPagesFinished(pagesFinished);
            return bookProgressRepository.save(currentBookProgress);
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
        Book book = bookRepository.findByGoogleBookId(bookID);
        int totalPage = book.getTotalPage();

        BookProgress progress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        int pagesFinished = 0;
        if (progress != null) {
            pagesFinished = progress.getPagesFinished();
        }
        System.out.println("GET PROGRESS "+pagesFinished);
        System.out.println("TOTAL PAGE "+ totalPage);
        if (totalPage > 0) {
            int percentage = (int) (pagesFinished * 100 / totalPage);
            System.out.println("PERCENTAGE "+percentage);
            return percentage;
        }
        return 0;
    }
}
