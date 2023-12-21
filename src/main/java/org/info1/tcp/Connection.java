package org.info1.tcp;

import java.net.InetAddress;

public interface Connection {
    InetAddress getAddress();
    void send(Object objectToSend);
    void start();
    void close();
    String getId();
}
