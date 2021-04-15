package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import edu.cmu.sda.bookreader.entity.RecommendedBookshelf;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.repository.BookshelfRepository;
import edu.cmu.sda.bookreader.repository.RecommendedBookshelfRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class BookshelfFactory extends BaseBookshelfFactory {
    @Autowired
    private BookshelfRepository bookshelfRepository;
    private RecommendedBookshelfRepository recommendedBookshelfRepository;

    public BookshelfRepository getBookshelfRepository(String type) {
        if (type.equals("Recommended")) {
            // create Recommended bookshelf
            return recommendedBookshelfRepository;
        }
        return bookshelfRepository;
    }
}
