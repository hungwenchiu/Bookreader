//package edu.cmu.sda.bookreader.service;
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.ConnectListener;
//
//import java.util.Collection;
//
//
//public class Socketio {
//
//    private static SocketIOServer socketIOServer;
//
//    public void startSocketio() {
//
//        // 配置
//        Configuration conf = new Configuration();
//        // 指定要主機ip地址，這個和頁面請求ip地址一致
//        conf.setHostname( "localhost" );
//        // 指定埠號
//        conf.setPort(9092);
//        // 設定最大的WebSocket幀內容長度限制
//        conf.setMaxFramePayloadLength( 1024 * 1024 );
//        // 設定最大HTTP內容長度限制
//        conf.setMaxHttpContentLength( 1024 * 1024 );
//
//        socketIOServer = new SocketIOServer( conf );
//
//        ConnectListener connect = new ConnectListener() {
//
//            @Override
//            public void onConnect( SocketIOClient client ) {}
//        };
//        // 新增客戶端
//        socketIOServer.addConnectListener( connect );
//        socketIOServer.start();
//    }
//
//    public void pushArr( String type, Object content ) {
//
//        // 獲取全部客戶端
//        Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
//        for( SocketIOClient socket : allClients ) {
//            socket.sendEvent( type, content );
//        }
//    }
//
//    public void startServer() {
//
//        if( socketIOServer == null ) {
//            new Thread( new Runnable() {
//
//                @Override
//                public void run() {
//
//                    startSocketio();
//                }
//            } ).start();
//        }
//    }
//
//    public void stopSocketio() {
//
//        if( socketIOServer != null ) {
//            socketIOServer.stop();
//            socketIOServer = null;
//        }
//    }
//
//}
