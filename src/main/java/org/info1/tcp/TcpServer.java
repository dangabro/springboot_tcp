package org.info1.tcp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
public class TcpServer implements Server, Callback {
    private static Log logger = LogFactory.getLog(TcpServer.class);

    private ServerSocket serverSocket;
    private volatile boolean isStop;
    private Map<String, Connection> connections;

    public TcpServer() {
        connections = new HashMap<>();
    }

    public void setPort(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server start at port " + port);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("May be port " + port + " busy.");
        }
    }

    @Override
    public void start() {
        new Thread(() -> {
            while (!isStop) {
                try {
                    Socket socket = serverSocket.accept();
                    if (socket.isConnected()) {
                        TcpConnection tcpConnection = new TcpConnection(socket, this);
                        tcpConnection.start();
                        connections.put(tcpConnection.getId(), tcpConnection);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        isStop = true;
    }

    @Override
    public void removeConnection(String id) {
        System.out.println("remove connection:" + id);
        connections.remove(id);
    }
}
