package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByGoogleBookId (String googleBookId);
}
