package edu.cmu.sda.bookreader.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Event handlers
 */
@Component
public class MessageEventHandler {

    /**
     * store subsribers socket information
     * key: user id, value: socket id
     */
    Map<String, SocketIOClient> userToSockList = new HashMap<>();

    /**
     * I also create a map to store sockid and user list
     * (cuz if a user uses the same browser, they will have the same socket io)
     */
    Map<SocketIOClient, String> sockToUserList = new HashMap<>();


    /**
     * socket io connection topic
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userid = client.getHandshakeData().getSingleUrlParam("userId");
        System.out.println("socketio connection - userid:  " + userid);

        userToSockList.put(userid, client);
        sockToUserList.put(client, userid);

        userToSockList.forEach((k, v) -> {
            System.out.println("User ID: " + k + "   Socket Id: " + v);
        });

    }

    /**
     * socket io onDisconnect topic
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {

        // remove the connection of the client
        if (sockToUserList.containsKey(client)) {
            System.out.println("socketio disconnection - userid:  " + sockToUserList.get(client));
            userToSockList.remove(sockToUserList.get(client));
            sockToUserList.remove(client);
        }
    }

    /**
     * socket io topic for timeline new post
     * @param client
     * @param request
     * @param msg
     */
    @OnEvent("newPost")
    public void newPost(SocketIOClient client, AckRequest request, MessageInfo msg) {
        String userids = msg.getMsgContent();

        for (String uid : userids.split(",")) {
            if (userToSockList.containsKey(uid))
                userToSockList.get(uid).sendEvent("updateTimelinePage", "true");
        }
    }

    /**
     * socket io topic for newReply
     * @param client
     * @param request
     * @param msg
     */
    @OnEvent("newReply")
    public void newReply(SocketIOClient client, AckRequest request, MessageInfo msg) {
        String userids = msg.getMsgContent();

        for (String uid : userids.split(",")) {
            if (userToSockList.containsKey(uid))
                userToSockList.get(uid).sendEvent("refreshReply", "true");
        }
    }

    /**
     * socket io topic for refreshFriendPage
     * @param client
     * @param request
     * @param msg
     */
    @OnEvent("refreshFriendPage")
    public void refreshFriendPage(SocketIOClient client, AckRequest request, MessageInfo msg) {
        String userids = msg.getMsgContent();

        for (String uid : userids.split(",")) {
            if (userToSockList.containsKey(uid))
                userToSockList.get(uid).sendEvent("updateFriendPage", uid);
        }
    }

    /**
     * socekt io topic for user login
     * @param client
     * @param request
     * @param msg
     */
    @OnEvent("newComer")
    public void newComer(SocketIOClient client, AckRequest request, MessageInfo msg) {
        String userid = msg.getMsgContent();

        for (Map.Entry<String, SocketIOClient> entry : userToSockList.entrySet()) {
            entry.getValue().sendEvent("userLogin", userid);
        }
    }
}