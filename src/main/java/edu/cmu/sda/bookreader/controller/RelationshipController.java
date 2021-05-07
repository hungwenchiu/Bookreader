package edu.cmu.sda.bookreader.controller;

import edu.cmu.sda.bookreader.entity.Relationship;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.service.RelationshipService;
import edu.cmu.sda.bookreader.service.UserService;
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

    @Qualifier("userService")
    @Autowired
    private UserService userService;

    /**
     * get all incoming friend requests
     * @param userId
     * @return
     */
    @GetMapping("/relationship/incoming/{userId}")
    public List<User> getAllIncomingRequest(@PathVariable long userId) {
        return service.getAllPendingFriendRequests(userService.getUser(userId));
    }

    /**
     * get all outgoing friend requests
     * @param userId
     * @return
     */
    @GetMapping("/relationship/outgoing/{userId}")
    public List<User> getAllOutgoingRequest(@PathVariable long userId) {
        return service.getAllPendingSentFriendRequests(userService.getUser(userId));
    }

    /**
     * get all friends
     * @param userId
     * @return
     */
    @GetMapping("/relationship/friends/{userId}")
    public List<User> getAllFriends(@PathVariable long userId) {
        return service.getAllFriends(userService.getUser(userId));
    }

    /**
     * get all user has no relationship with the given user
     * @param userId
     * @return
     */
    @GetMapping("/relationship/none/{userId}")
    public List<User> getAllNotFriends(@PathVariable long userId) {
        return service.getNotFriends(userService.getUser(userId));
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
