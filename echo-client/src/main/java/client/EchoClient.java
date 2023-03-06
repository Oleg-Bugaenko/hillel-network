package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoClient implements AutoCloseable {
    private Socket socket;

    public EchoClient(String host, int port) throws IOException {
        socket = new Socket(host, port);

        try (InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            String response = bufferedReader.readLine();            //читаємо дані із сервера
            System.out.println(response);

        }
    }

    @Override
    public void close() throws Exception {
        if (!socket.isClosed()) socket.close();
    }
}
