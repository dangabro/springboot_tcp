package org.info1.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class TcpServerAutoStarterApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Server server;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("starting server...");
        server.setPort(31000);
        server.start();
        System.out.println("server started");
    }
}
