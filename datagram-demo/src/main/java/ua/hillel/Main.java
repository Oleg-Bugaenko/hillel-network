package ua.hillel;

import ua.hillel.server.UdpEchoServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (UdpEchoServer udpEchoServer = new UdpEchoServer(8080)) {



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}