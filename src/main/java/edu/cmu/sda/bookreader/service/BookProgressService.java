package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.BookProgress;
import edu.cmu.sda.bookreader.repository.BookProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // initialize Progress for a book for a user
    public BookProgress initializeBookProgressForUser(Long userID, String bookID) {
        BookProgress bookProgress = new BookProgress();
        bookProgress.setGoogleBookID(bookID);
        bookProgress.setUserID(userID);
        bookProgress.setPagesFinished(0);
        bookProgressRepository.save(bookProgress);
        return bookProgress;
    }

    // update Progress for a book for a user
    public int updateBookProgressForUser(Long userID, String bookID, int pagesFinished) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        if (currentBookProgress != null) {
            currentBookProgress.setPagesFinished(pagesFinished);
            bookProgressRepository.save(currentBookProgress);
            return this.calculateProgress(userID, bookID);
        }
        return 0;
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
            percentage = percentage > 100 ? 100 : percentage;
            return percentage;
        }
        return 0;
    }
}
