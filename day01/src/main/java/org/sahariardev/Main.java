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

                        byte[] buffer = new byte[8 * 1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                            outputStream.flush();
                        }

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