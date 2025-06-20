package com.edaakyil.java.app.imageprocessing.client.runner;

import com.edaakyil.java.app.imageprocessing.client.Client;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;

@Configuration
public class ClientRunner implements ApplicationRunner {
    private final Client m_client;
    private final ExecutorService m_executorService;

    public ClientRunner(Client client, ExecutorService executorService)
    {
        m_client = client;
        m_executorService = executorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        m_executorService.execute(m_client::run);
    }
}
