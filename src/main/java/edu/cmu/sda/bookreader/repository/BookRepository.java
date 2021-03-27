package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
}
