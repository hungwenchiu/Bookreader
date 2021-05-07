package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.BookProgress;
import edu.cmu.sda.bookreader.repository.BookProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component(value = "bookProgressService")
public class BookProgressService {
    @Autowired
    BookProgressRepository bookProgressRepository;

    /* TO DO FOR SHREYA-initialize progress for a book when user adds it in the bookshelf*/
    // initialize Progress for a book for a user
    public BookProgress initializeBookProgressForUser(Long userID, String bookID) {
        BookProgress bookProgress = new BookProgress();
        bookProgress.setGoogleBookID(bookID);
        bookProgress.setUserID(0);
        bookProgress.setPagesFinished(0);

        return bookProgressRepository.save(bookProgress);
    }

    // update Progress for a book for a user
    public BookProgress updateBookProgressForUser(Long userID, String bookID, Long pagesFinished) {
        BookProgress currentBookProgress = bookProgressRepository.getBookProgressForUserByBook(userID, bookID);
        System.out.println(currentBookProgress);
        if (currentBookProgress != null) {
            currentBookProgress.setPagesFinished(pagesFinished);
            return bookProgressRepository.save(currentBookProgress);
        }
        return null;
    }

    // calculate progress for a book for a user
//    public Long getBookProgressForUser() {
//        bookProgressRepository.getBookProgressForUserByBook(userID, )
//    }
}
