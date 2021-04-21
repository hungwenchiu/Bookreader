package edu.cmu.sda.bookreader.repository;
import edu.cmu.sda.bookreader.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookshelfRepository<T> extends JpaRepository<Bookshelf, Long> {
}
