import server.EchoServer;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        try (EchoServer echoServer = new EchoServer(8080)) {
            System.out.println("Create");



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
