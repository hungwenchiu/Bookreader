package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.service.RelationshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Scope(value = "session")
@Component(value = "relationshipController")
@Slf4j
public class RelationshipController {
    @Qualifier("relationshipService")
    @Autowired
    private RelationshipService service;

    /**
     * get all incoming friend requests
     * @param user
     * @return
     */
    @PostMapping("/relationship/incoming")
    public List<User> getAllIncomingRequest(@RequestBody User user) {
        return service.getAllPendingFriendRequests(user);
    }

    /**
     * get all outgoing friend requests
     * @param user
     * @return
     */
    @PostMapping("/relationship/outgoing")
    public List<User> getAllOutgoingRequest(@RequestBody User user) {
        return service.getAllPendingSentFriendRequests(user);
    }

    /**
     * get all friends
     * @param user
     * @return
     */
    @PostMapping("/relationship/friends")
    public List<User> getAllFriends(@RequestBody User user) {
        return service.getAllFriends(user);
    }

    /**
     * get all user has no relationship with the given user
     * @param user
     * @return
     */
    @PostMapping("/relationship/none")
    public List<User> getAllNotFriends(@RequestBody User user) {
        List<User> users = service.getNotFriends(user);
        users.remove(user);
        return users;
    }

    /**
     * send a friend request fromUser toUser
     * @param users fromUser and toUser
     * @return the created relationship
     */
    @PostMapping("/relationship")
    public Relationship addFriend(@RequestBody List<User> users) {
        return service.saveFriendRequest(users.get(0), users.get(1));
    }

    /**
     * accept a friend request fromUser toUser
     * @param users fromUser and toUser
     * @return the updated relationship
     */
    @PostMapping("/relationship/accept")
    public Map<String, String> acceptFriend(@RequestBody List<User> users) {
        service.acceptRequest(users.get(0), users.get(1));
        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }

    /**
     * reject a friend request fromUser toUser
     * @param users fromUser and toUser
     * @return the updated relationship
     */
    @DeleteMapping("/relationship")
    public Map<String, String> rejectFriend(@RequestBody List<User> users) {
        service.rejectRequest(users.get(0), users.get(1));
        Map<String, String> result = new HashMap<>();
        result.put("status", "success");
        return result;
    }
}
