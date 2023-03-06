package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements AutoCloseable, ChatConnectionListener{
    private final ServerSocket serverSocket;

    private final List<ChatConnection> connections = new ArrayList<>();

    //створення об'єкту сервера
    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();              //сервер постійно прослуховує порт та чекає на з'єднання
            ChatConnection chatConnection = new ChatConnection(socket, this);   //при отриманні з'єднання створюється chatConnection
        }
    }



    @Override
    public void close() throws Exception {
        if (!serverSocket.isClosed()) serverSocket.close();
    }

    @Override
    public void onConnect(ChatConnection connection) {
        System.out.println("Entered the chat" + connection);
        connections.forEach(client -> {
            if (!client.equals(connection)) {
                client.sendMassage(String.format("-- [%s]: entered the chat%n", connection.getName()));
            }
        });
        connections.add(connection);
    }

    @Override
    public void onMessage(ChatConnection connection, String message) {
        connections.forEach(client -> {
            if (!client.equals(connection)) {
               client.sendMassage(String.format("[%s]: %s%n", connection.getName(), message));
            }
        });

    }

    @Override
    public void onDisconnect(ChatConnection connection) {
        connections.forEach(client -> {
            if (!client.equals(connection)) {
                client.sendMassage(String.format("-- [%s]: left the chat%n", connection.getName()));
            }
        });
        connections.remove(connection);
        System.out.println("Exit the chat" + connection);
    }
}
