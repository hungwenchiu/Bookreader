package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.BookProgress;
import edu.cmu.sda.bookreader.repository.BookProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Scope(value = "session")
@Component(value = "bookProgressService")
public class BookProgressService {
    @Autowired
    BookProgressRepository bookProgressRepository;

    @Qualifier("bookService")
    @Autowired
    private BookService bookService;

    /**
     * initialize Progress for a book for a user
     * @param userID
     * @param bookID
     * @return
     */
    public BookProgress initializeBookProgressForUser(Long userID, String bookID) {
        BookProgress bookProgress = new BookProgress();
        bookProgress.setGoogleBookID(bookID);
        bookProgress.setUserID(userID);
        bookProgress.setPagesFinished(0);
        bookProgressRepository.save(bookProgress);
        return bookProgress;
    }

    /**
     * update Progress for a book for a user
     * @param userID
     * @param bookID
     * @param pagesFinished
     * @return
     */
    public int updateBookProgressForUser(Long userID, String bookID, int pagesFinished) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        if (currentBookProgress != null) {
            currentBookProgress.setPagesFinished(pagesFinished);
            bookProgressRepository.save(currentBookProgress);
            return this.calculateProgress(userID, bookID);
        }
        return 0;
    }

    /**
     * get book progress
     * @param userID
     * @param bookID
     * @return
     */
    public int getPagesFinished(Long userID, String bookID) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        if (currentBookProgress != null) {
            return currentBookProgress.getPagesFinished();
        }
        return -1;
    }

    /**
     * calculate book progress in percentage
     * @param userID
     * @param bookID
     * @return
     */
    public int calculateProgress(Long userID, String bookID) {
        int totalPage = bookService.getTotalPage(bookID);

        BookProgress progress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        int pagesFinished = 0;
        if (progress != null) {
            pagesFinished = progress.getPagesFinished();
        }

        if (totalPage > 0) {
            int percentage = pagesFinished * 100 / totalPage;
            percentage = percentage > 100 ? 100 : percentage;
            return percentage;
        }
        return 0;
    }
}
