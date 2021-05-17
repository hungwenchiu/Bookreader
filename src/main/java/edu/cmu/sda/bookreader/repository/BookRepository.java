package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * find book by google book id
     * @param googleBookId
     * @return
     */
    Book findByGoogleBookId (String googleBookId);
}
