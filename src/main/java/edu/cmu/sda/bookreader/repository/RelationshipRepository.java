package edu.cmu.sda.bookreader.repository;

import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    /**
     * accept friend request
     * @param fromUser
     * @param toUser
     */
    @Modifying
    @Query("UPDATE Relationship r set r.status = 1, r.actionUser = :toUser where r.userOne = :fromUser AND r.userTwo = :toUser")
    void acceptRequest(@Param("fromUser") User fromUser, @Param("toUser") User toUser);

    /**
     * delete incoming friend request
     * @param fromUser
     * @param toUser
     */
    @Modifying
    @Query("DELETE from Relationship r where (r.userOne = :fromUser AND r.userTwo = :toUser) or (r.userOne = :toUser AND r.userTwo = :fromUser)")
    void rejectRequest(@Param("fromUser") User fromUser, @Param("toUser") User toUser);

    /**
     * get all friend relationship for user
     * @param user
     * @return
     */
    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 1")
    List<Relationship> getAllFriendRelationship(@Param("user") User user);

    /**
     * get all pending friend request for a user
     * @param user
     * @return
     */
    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser <> :user")
    List<Relationship> getAllPendingRequest(@Param("user") User user);

    /**
     * get all pending friend request sent by user
     * @param user
     * @return
     */
    @Query(value = "SELECT r from Relationship r where (r.userOne = :user or r.userTwo = :user) and r.status = 0 and r.actionUser = :user")
    List<Relationship> getAllPendingSentRequest(@Param("user") User user);

    /**
     * get a list of users who are not friend with given user
     * @param user
     * @return
     */
    @Query(value = "SELECT * FROM user_info WHERE id NOT IN ( " +
            "SELECT DISTINCT user_one FROM user_info u JOIN relationship r ON u.id=r.user_one OR u.id=r.user_two WHERE u.id=:user UNION ALL " +
            "SELECT DISTINCT user_two FROM user_info u JOIN relationship r ON u.id=r.user_one OR u.id=r.user_two WHERE u.id=:user)",
            nativeQuery = true)
    List<List<Object>> getNoRelationship(@Param("user") User user);
}
