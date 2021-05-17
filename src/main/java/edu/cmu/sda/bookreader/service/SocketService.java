package edu.cmu.sda.bookreader.service;

import com.corundumstudio.socketio.SocketIOServer;

public class SocketService {

    private static SocketService socketService; // singleton
    public SocketIOServer server;

    public static SocketService getInstance() {
        if (socketService == null)
            socketService = new SocketService();

        return socketService;
    }
}
