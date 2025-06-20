package com.edaakyil.java.app.imageprocessing.server.runner;

import com.edaakyil.java.app.imageprocessing.server.Server;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class ServerRunner implements ApplicationRunner {
    private final Server m_server;
    private final ExecutorService m_executorService;

    public ServerRunner(Server server, ExecutorService executorService)
    {
        m_server = server;
        m_executorService = executorService;
    }

    @Override
    public void run(ApplicationArguments args)
    {
        m_executorService.execute(m_server::start);
    }
}
