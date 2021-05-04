package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Scope(value = "session")
@Component(value = "relationshipService")
public class RelationshipService {
    @Autowired
    private RelationshipRepository repository;

    /**
     * save the friend request
     * @param fromUser friend request invitor
     * @param toUser friend request invitee
     * @return the new relationship
     */
    public Relationship saveFriendRequest(User fromUser, User toUser) {
        Relationship relationship = new Relationship();
        relationship.setUserOne(fromUser);
        relationship.setUserTwo(toUser);
        relationship.setStatus(Relationship.Status.PENDING);
        relationship.setActionUser(fromUser);
        return repository.save(relationship);
    }

    /**
     * "toUser" accept friend request from "fromUser"
     * @param fromUser friend request invitor
     * @param toUser the user accepting the request
     */
    public void acceptRequest(User fromUser, User toUser) {
        repository.acceptRequest(fromUser, toUser);
    }

    /**
     * "toUser" reject friend request from "fromUser"
     * @param fromUser friend request invitor
     * @param toUser the user rejecting the request
     */
    public void rejectRequest(User fromUser, User toUser) {
        repository.rejectRequest(fromUser, toUser);
    }

    /**
     * get the user's friend list
     * @param user user
     * @return a list of user who is friend with provided user
     */
    public List<User> getAllFriends(User user) {
        List<Relationship> friendRelationships = repository.getAllFriendRelationship(user);
        List<User> friendList = new ArrayList<>();
        for (Relationship relationship : friendRelationships) {
            User userOne = relationship.getUserOne();
            User userTwo = relationship.getUserTwo();
            friendList.add(userOne.getId() == user.getId() ? userTwo : userOne);
        }
        return friendList;
    }

    /**
     * Get the entire pending user request for the user from other users
     * @param user user
     * @return list of user who send request to current user with pending status
     */
    public List<User> getAllPendingFriendRequests(User user) {
        List<Relationship> pendingRelationships = repository.getAllPendingRequest(user);
        List<User> pendingList = new ArrayList<>();
        for (Relationship relationship : pendingRelationships) {
            User userOne = relationship.getUserOne();
            User userTwo = relationship.getUserTwo();
            pendingList.add(userOne.getId() == user.getId() ? userTwo : userOne);
        }
        return pendingList;
    }

    /**
     * Get the entire pending friend request sent by current user
     * @param user user
     * @return a list of users whom the input user send friend request to
     */
    public List<User> getAllPendingSentFriendRequests(User user) {
        List<Relationship> pendingRelationships = repository.getAllPendingSentRequest(user);
        List<User> pendingList = new ArrayList<>();
        for (Relationship relationship : pendingRelationships) {
            User userOne = relationship.getUserOne();
            User userTwo = relationship.getUserTwo();
            pendingList.add(userOne.getId() == user.getId() ? userTwo : userOne);
        }
        return pendingList;
    }

    /**
     * Get the all users who has no relationship with the given user
     * @param user user
     * @return a list of users who has no relationship with the given user
     */
    public List<User> getNotFriends(User user) {
        List<List<Object>> dbResult = repository.getNoRelationship(user);
        List<User> result = new ArrayList<>();
        dbResult.stream().forEach((r) -> {
            User u = new User();
            u.setId(((BigInteger) r.get(0)).longValue());
            u.setName(String.valueOf(r.get(1)));
            result.add(u);
        });
        return result;
    }

//    /**
//     * Get all users that has no relationship with the given user
//     * @param user user
//     * @return a list of users who has no relationship with
//     */
//    public List<User> getAllUserWithNoRelationship(User user) {
//        List<Relationship> noRelationship = repository.getAllPendingSentRequest(user);
//        List<User> pendingList = new ArrayList<>();
//        for (Relationship relationship : pendingRelationships) {
//            User userOne = relationship.getUserOne();
//            User userTwo = relationship.getUserTwo();
//            pendingList.add(userOne.equals(user) ? userTwo : userOne);
//        }
//        return pendingList;
//    }
}
