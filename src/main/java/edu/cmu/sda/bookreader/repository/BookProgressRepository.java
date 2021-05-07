package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.BookProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookProgressRepository extends JpaRepository<BookProgress, Long> {
    @Query(value = "SELECT p from BookProgress p where (p.userID = :userID AND p.googleBookID = :bookID)")
    BookProgress getBookProgressForUserByBook(Long userID, String bookID);
}
