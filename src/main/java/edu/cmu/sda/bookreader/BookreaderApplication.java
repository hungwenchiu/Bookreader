package edu.cmu.sda.bookreader;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import edu.cmu.sda.bookreader.service.SocketService;
import edu.cmu.sda.bookreader.socketio.MessageEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class BookreaderApplication {

	@Resource
	private MessageEventHandler messageEventHandler;

	public static void main(String[] args) {
		SpringApplication.run(BookreaderApplication.class, args);
	}

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		SocketConfig socketConfig = new SocketConfig();
		socketConfig.setReuseAddress(true);
		socketConfig.setTcpNoDelay(true);
		socketConfig.setSoLinger(0);
		config.setSocketConfig(socketConfig);
		config.setHostname("localhost");
		config.setPort(9092);
		config.setBossThreads(1);
		config.setWorkerThreads(100);
		config.setAllowCustomRequests(true);

		SocketService socketService = new SocketService();

		socketService.server = new SocketIOServer(config);
		socketService.server.addListeners(messageEventHandler);
		return socketService.server;
	}

	/**
	 * @param socketServer
	 * @return
	 */
	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}

}
