package com.edaakyil.java.app.imageprocessing.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class Server {
    private final ExecutorService m_executorService;

    public Server(ExecutorService executorService)
    {
        m_executorService = executorService;
    }

    /**
     * Starts the server
     */
    public void start()
    {
        log.info("Server starting...");
    }
}
