package org.sahariardev;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
                        
                        HttpRequest httpRequest = HttpHandler.parsetHttpRequest(clientInputStream);
                        System.out.println("--------request-------");
                        System.out.println(new String(httpRequest.toByteArray(), "UTF-8"));
                        writeRequest(targetOutputStream, httpRequest);

                    } catch (IOException e) {
                        System.out.println("error copying stream");
                    }
                });

                Future<?> downStreamFuture = executor.submit(() -> {
                    try {
                        HttpResponse httpResponse = HttpHandler.parseHttpResponse(targetInputStream);
                        System.out.println("--------response-------");
                        System.out.println(new String(httpResponse.toByteArray(), "UTF-8"));
                        writeResponse(clientOutputStream, httpResponse);
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

    private void writeRequest(OutputStream outputStream, HttpRequest httpRequest) throws IOException {
        outputStream.write(httpRequest.toByteArray());
        outputStream.flush();
    }

    public void writeResponse(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        outputStream.write(httpResponse.toByteArray());
        outputStream.flush();
    }

}
