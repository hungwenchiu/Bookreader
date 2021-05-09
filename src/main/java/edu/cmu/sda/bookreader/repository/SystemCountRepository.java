package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.SystemCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemCountRepository extends JpaRepository<SystemCount, Long> {
    SystemCount findById (long Id);
    SystemCount findByGoogleBookId (String googleBookId);
    List<SystemCount> findTop10ByOrderByReadingCountDesc();
    List<SystemCount> findTop10ByOrderByFavouriteCountDesc();
    List<SystemCount> findTop10ByOrderByReadCountDesc();
    List<SystemCount> findTop10ByOrderByWantToReadCountDesc();
}
