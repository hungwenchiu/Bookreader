package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.RecommendedBookshelf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedBookshelfRepository extends JpaRepository<RecommendedBookshelf, Long> {
    RecommendedBookshelf findByBookshelfUserID(Long userID);
}
