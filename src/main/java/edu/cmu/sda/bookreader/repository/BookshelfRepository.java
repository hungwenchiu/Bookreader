package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookshelfRepository<T> extends JpaRepository<Bookshelf, Long> {
    Book save(Book book);

    /**
     * find all bookshelves belong to the given user
     * @param userID
     * @return
     */
    List<Bookshelf> findByBookshelfUserID(Long userID);

    /**
     * retrive a book shelf with name for user with userID
     * @param name
     * @param userID
     * @return
     */
    @Query(value = "SELECT b from Bookshelf b where (b.name = :name and b.bookshelfUserID = :userID)")
    Bookshelf findBookshelfByNameForUser(String name, Long userID);
}
