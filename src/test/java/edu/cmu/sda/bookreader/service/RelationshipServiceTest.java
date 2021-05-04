package edu.cmu.sda.bookreader.service;

import edu.cmu.sda.bookreader.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RelationshipServiceTest {
    @Qualifier("relationshipService")
    @Autowired
    RelationshipService relationshipService;

    @Qualifier("userService")
    @Autowired
    UserService userService;

    User userA;
    User userB;

    @Before
    public void setup() {
        userA = new User();
        userB = new User();
        userA.setName("userA");
        userB.setName("userB");
        userA.setPassword("userAPw");
        userB.setPassword("userBPw");
        userService.saveUser(userA);
        userService.saveUser(userB);
    }


    @Test
    public void canSendFriendRequest() {
        relationshipService.saveFriendRequest(userA, userB);
        List<User> pendingSentRequest = relationshipService.getAllPendingSentFriendRequests(userA);
        List<User> pendingRequest = relationshipService.getAllPendingFriendRequests(userB);

        assertEquals(1, pendingSentRequest.size());
        assertEquals(1, pendingRequest.size());
        assertEquals(userA, pendingRequest.get(0));
        assertEquals(userB, pendingSentRequest.get(0));
    }

    @Test
    public void canAcceptFriendRequest() {
        relationshipService.saveFriendRequest(userA, userB);
        relationshipService.acceptRequest(userA, userB);
        List<User> friendListA = relationshipService.getAllFriends(userA);
        List<User> friendListB = relationshipService.getAllFriends(userB);
        List<User> pendingSentRequest = relationshipService.getAllPendingSentFriendRequests(userA);
        List<User> pendingRequest = relationshipService.getAllPendingFriendRequests(userB);

        assertEquals(0, pendingSentRequest.size());
        assertEquals(0, pendingRequest.size());
        assertEquals(1, friendListA.size());
        assertEquals(1, friendListB.size());

        assertEquals(userB, friendListA.get(0));
        assertEquals(userA, friendListB.get(0));
    }
}
