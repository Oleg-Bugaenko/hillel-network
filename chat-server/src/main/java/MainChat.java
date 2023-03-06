import server.ChatServer;

import java.io.IOException;

public class MainChat {
    public static void main(String[] args) {
        try (ChatServer chatServer = new ChatServer(8080)) {




        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
