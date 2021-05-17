package edu.cmu.sda.bookreader.repository;


import edu.cmu.sda.bookreader.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * find all events related with user
     * @param userid
     * @return
     */
    @Modifying
    @Query(value = "SELECT e FROM Event e WHERE e.userid= :userid ORDER BY e.time DESC")
    List<Event> findByUserid(@Param("userid") String userid);

    /**
     * find all events that are related with the given user list
     * @param useridlist
     * @return
     */
    @Query(value = "SELECT e FROM Event e WHERE e.userid IN (:useridlist) ORDER BY e.time DESC")
    List<Event> findByMultipleUsers(@Param("useridlist") List<String> useridlist);
    
}
