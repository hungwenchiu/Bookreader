package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    @Modifying
    @Query("UPDATE Relationship r set r.status = 1, r.actionUser = :toUser where r.userOne = :fromUser AND r.userTwo = :toUser")
    void acceptRequest(@Param("fromUser") User fromUser, @Param("toUser") User toUser);

    @Modifying
    @Query("DELETE from Relationship r where (r.userOne = :fromUser AND r.userTwo = :toUser) or (r.userOne = :toUser AND r.userTwo = :fromUser)")
    void rejectRequest(@Param("fromUser") User fromUser, @Param("toUser") User toUser);

    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 1")
    List<Relationship> getAllFriendRelationship(@Param("user") User user);

    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser <> :user")
    List<Relationship> getAllPendingRequest(@Param("user") User user);

    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser = :user")
    List<Relationship> getAllPendingSentRequest(@Param("user") User user);

    @Query(value = "SELECT * FROM USER WHERE id NOT IN ( " +
            "SELECT DISTINCT user_one FROM USER u JOIN relationship r ON u.id=r.user_one OR u.id=r.user_two WHERE u.id=:user UNION ALL " +
            "SELECT DISTINCT user_two FROM USER u JOIN relationship r ON u.id=r.user_one OR u.id=r.user_two WHERE u.id=:user)",
            nativeQuery = true)
    List<List<Object>> getNoRelationship(@Param("user") User user);

//    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser = :user")
//    List<Relationship> getAllPendingSentRequest(@Param("user") User user);
}
