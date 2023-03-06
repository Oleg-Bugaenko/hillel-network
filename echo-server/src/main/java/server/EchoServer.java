package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class EchoServer implements AutoCloseable {
    private ServerSocket serverSocket;

    public EchoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            try (Socket socket = serverSocket.accept()) {
                try (OutputStream outputStream = socket.getOutputStream();
                     PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream))) {

                    printWriter.println("Echo response "+ LocalDateTime.now());
                    printWriter.flush();
                }

            }

        }

    }


    @Override
    public void close() throws Exception {
        if (!serverSocket.isClosed()) serverSocket.close();
    }
}
