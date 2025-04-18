package org.sahariardev;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888);) {
            Loadbalancer lb = new Loadbalancer();
            Host host1 = new Host("localhost", 9090);
            Host host2 = new Host("localhost", 9091);

            lb.add(host1);
            lb.add(host2);

            while (true) {
                Socket socket = serverSocket.accept();
                lb.forward(socket);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}