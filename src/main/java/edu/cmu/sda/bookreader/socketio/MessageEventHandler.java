package edu.cmu.sda.bookreader.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import edu.cmu.sda.bookreader.entity.User;
import edu.cmu.sda.bookreader.repository.RelationshipRepository;
import edu.cmu.sda.bookreader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Event handlers
 */
@Component
public class MessageEventHandler {

    // store subsribers socket information
    // key: user id, value: socket id
    Map<String, SocketIOClient> userToSockList = new HashMap<>();

    // I also create a map to store sockid and user list
    // (cuz if a user uses the same browser, they will have the same socket io)
    Map<SocketIOClient, String> sockToUserList = new HashMap<>();


    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userid = client.getHandshakeData().getSingleUrlParam("userId");
                System.out.println("socketio connection - userid:  " + userid);

        userToSockList.put(userid, client);
        sockToUserList.put(client, userid);

        userToSockList.forEach((k, v) -> {
            System.out.println("User ID: " + k  + "   Socket Id: " + v);
        });

    }
    /**
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {

        // remove the connection of the client
        if(sockToUserList.containsKey(client))
        {
            System.out.println("socketio disconnection - userid:  " + sockToUserList.get(client));
            userToSockList.remove(sockToUserList.get(client));
            sockToUserList.remove(client);
        }
    }

    @OnEvent("messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo msg) {
        System.out.println("Got it" + msg.getMsgContent());
        client.sendEvent("Broadcast", "4554444564546454564545646");

    }

    @OnEvent("newPost")
    public void newPost(SocketIOClient client, AckRequest request, MessageInfo msg) {
        String userids = msg.getMsgContent();

        for(String uid: userids.split(",")) {
            if(userToSockList.containsKey(uid))
                userToSockList.get(uid).sendEvent("updateTimelinePage", "true");
        }
    }

    @OnEvent("newReply")
    public void newReply(SocketIOClient client, AckRequest request, MessageInfo msg) {
        String userids = msg.getMsgContent();

        for(String uid: userids.split(",")) {
            if(userToSockList.containsKey(uid))
                userToSockList.get(uid).sendEvent("refreshReply", "true");
        }
    }
}