package org.sahariardev;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888);
             ExecutorService executorService = Executors.newCachedThreadPool();) {

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> {

                    try (InputStream inputStream = socket.getInputStream();
                         OutputStream outputStream = socket.getOutputStream()) {

                        byte[] requestBytes = inputStream.readAllBytes();
                        String request = new String(requestBytes, StandardCharsets.UTF_8);

                        System.out.println("Received Request: \n\n"+request);

                        outputStream.write(requestBytes);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}