package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(long userId);

    Review findById(long Id);

    List<Review> findByGoogleBookId(String googleBookId);
}
