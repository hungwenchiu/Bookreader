package edu.cmu.sda.bookreader.repository;


import edu.cmu.sda.bookreader.entity.Event;
import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Modifying
    @Query(value = "SELECT e FROM Event e WHERE e.userid= :userid ORDER BY e.time DESC")
    List<Event> findByUserid(@Param("userid") String userid);

    @Query(value = "SELECT e FROM Event e WHERE e.userid IN (:useridlist) ORDER BY e.time DESC")
    List<Event> findByMultipleUsers(@Param("useridlist") List<String> useridlist);

//    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 1")
//    List<Relationship> getAllFriendRelationship(@Param("user") User user);
}
