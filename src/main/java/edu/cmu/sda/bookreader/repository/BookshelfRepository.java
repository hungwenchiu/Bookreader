package edu.cmu.sda.bookreader.repository;
import edu.cmu.sda.bookreader.entity.Book;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookshelfRepository<T> extends JpaRepository<Bookshelf, Long> {
    Book save(Book book);
}
