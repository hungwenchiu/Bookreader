package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.BookProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookProgressRepository extends JpaRepository<BookProgress, Long> {
    @Query(value = "SELECT p from BookProgress p WHERE (p.userID = :userID AND p.googleBookID = :bookID)")
    BookProgress getBookProgressForUserByBook(@Param("userID") Long userID, @Param("bookID") String bookID);
}
