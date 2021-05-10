//package edu.cmu.sda.bookreader.service;
//
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//@Configuration
//@WebListener
//public class SocketioLisener implements ServletContextListener {
//
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        //啟動Socketio服務
//        Socketio socketio = new Socketio();
//        socketio.startServer();
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        //關閉Socketio服務
//        Socketio socketio = new Socketio();
//        socketio.stopSocketio();
//    }
//
//}
