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

    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 1")
    List<Relationship> getAllFriendRelationship(@Param("user") User user);

    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser <> :user")
    List<Relationship> getAllPendingRequest(@Param("user") User user);

    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser = :user")
    List<Relationship> getAllPendingSentRequest(@Param("user") User user);
}
