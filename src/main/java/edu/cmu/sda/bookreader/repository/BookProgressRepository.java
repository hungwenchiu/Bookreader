package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.BookProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookProgressRepository extends JpaRepository<BookProgress, Long> {
    /**
     * retrive book reading progress by google book id
     * @param userID
     * @param bookID
     * @return
     */
    @Query(value = "SELECT p from BookProgress p WHERE (p.userID = :userID AND p.googleBookID = :bookID)")
    BookProgress getBookProgressForUserByBook(@Param("userID") Long userID, @Param("bookID") String bookID);
}
