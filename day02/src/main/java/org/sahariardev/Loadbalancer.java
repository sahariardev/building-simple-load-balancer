package org.sahariardev;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Loadbalancer {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final List<Host> hosts = new ArrayList<>();

    int currentHostIndex = 0;

    public synchronized void add(Host host) {
        hosts.add(host);
    }

    private synchronized Host getNextHost() {
        Host host = this.hosts.get(currentHostIndex);
        currentHostIndex = (currentHostIndex + 1) % this.hosts.size();

        return host;
    }

    public void forward(Socket serverSocket) {
        executor.submit(() -> {
            Host host = getNextHost();
            System.out.println("Forwarding to -> host: " + host.getHost() + " port: " + host.getPort());
            try (Socket targetSocket = new Socket(host.getHost(), host.getPort());
                 InputStream clientInputStream = serverSocket.getInputStream();
                 OutputStream clientOutputStream = serverSocket.getOutputStream();
                 InputStream targetInputStream = targetSocket.getInputStream();
                 OutputStream targetOutputStream = targetSocket.getOutputStream();) {

                Future<?> upStreamFuture = executor.submit(() -> {
                    try {
                        copyStream(clientInputStream, targetOutputStream, "request");
                    } catch (IOException e) {
                        System.out.println("error copying stream");
                    }
                });

                Future<?> downStreamFuture = executor.submit(() -> {
                    try {
                        copyStream(targetInputStream, clientOutputStream, "response");
                    } catch (IOException e) {
                        System.out.println("error copying stream");
                    }
                });

                upStreamFuture.get();
                downStreamFuture.get();

            } catch (IOException | ExecutionException | InterruptedException e) {
                System.out.println("error: " + e.getMessage());
            }
        });
    }

    private void copyStream(InputStream inputStream, OutputStream outputStream, String type) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        try (inputStream; outputStream) {
            while ((read = inputStream.read(buffer)) != -1) {
                System.out.println("--------" + type + "-------");
                System.out.println(new String(buffer, StandardCharsets.UTF_8));
                outputStream.write(buffer, 0, read);
                outputStream.flush();
            }
        } catch (SocketException e) {

        }
    }

}
