package org.info1.bean;

import org.info1.tcp.Server;
import org.info1.tcp.TcpServer;
import org.info1.tcp.TcpServerAutoStarterApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TcpServerConfig {

    @Bean
    TcpServerAutoStarterApplicationListener tcpServerAutoStarterApplicationListener() {
        return new TcpServerAutoStarterApplicationListener();
    }

    @Bean
    Server server(){
        return new TcpServer();
    }
}
