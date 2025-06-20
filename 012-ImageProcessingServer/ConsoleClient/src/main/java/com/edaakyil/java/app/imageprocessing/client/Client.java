package com.edaakyil.java.app.imageprocessing.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
@Slf4j
public class Client {
    @Value("${server.imageprocessing.host}")
    private String m_host;

    @Value("${server.imageprocessing.port}")
    private int m_port;

    public void run()
    {
        try (var socket = new Socket(m_host, m_port)) {
            log.info("Connected to {}:{}", m_host, m_port);
        } catch (IOException ex) {
            log.error("IO Problem occurred: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Problem occurred: {}", ex.getMessage());
        }
    }
}
