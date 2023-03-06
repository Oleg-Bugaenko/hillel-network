package server;

import java.io.*;
import java.net.Socket;

public class ChatConnection {
    private Socket socket;
    private ChatConnectionListener listener;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final String name;


    public ChatConnection(Socket socket, ChatConnectionListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;

        //запис даних
        InputStream inputStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));

        //читання даних
        OutputStream outputStream = socket.getOutputStream();
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        //запит імені користувача
        writer.write("Please enter your name? ");
        writer.flush();
        name = reader.readLine();


        //створення окремого потоку для користувача що увійшов в чат
        new Thread(() -> {
            listener.onConnect(ChatConnection.this);

            try {
                while (true) {                                              //читання повідомлень з чату
                    String massage = reader.readLine();
                    if (massage == null || massage.toLowerCase().equals("exit")) break;     //вихід з чату
                    listener.onMessage(ChatConnection.this, massage);
                }

            } catch (IOException e) {
               // throw new RuntimeException(e);
            } finally {
                listener.onDisconnect(ChatConnection.this);         //вихід з чату
            }
        }).start();
    }

    public String getName() {
        return name;
    }

    public void sendMassage(String massage) {
        try {
            writer.write(massage);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
