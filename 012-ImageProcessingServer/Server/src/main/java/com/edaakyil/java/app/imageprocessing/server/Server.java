package com.edaakyil.java.app.imageprocessing.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class Server {
    private final ExecutorService m_executorService;

    @Value("${app.server.port}")
    private int m_port;

    /**
     * Client ile ilgili işlemler bu akış içerisinde karşılanacak
     * @param socket
     */
    private void handleClient(Socket socket)
    {
        try (socket) {
            log.info("Client connected from {}:{}", socket.getInetAddress(), socket.getPort());
        } catch (IOException ex) {
            log.error("IO Problem occurred while client connected: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Problem occurred while client connected: {}", ex.getMessage());
        }
    }

    public Server(ExecutorService executorService)
    {
        m_executorService = executorService;
    }

    /**
     * Starts the server
     */
    public void start()
    {
        log.info("Starting server on port: {}", m_port);

        try (var serverSocket = new ServerSocket(m_port)) {
            while (true) {
                var socket = serverSocket.accept();

                m_executorService.execute(() -> handleClient(socket));
            }
        } catch (IOException ex) {
            log.error("IO Problem occurred while server is waiting: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Problem occurred while server is waiting: {}", ex.getMessage());
        }
    }
}
