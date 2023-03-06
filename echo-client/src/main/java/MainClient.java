import client.EchoClient;

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) {
        try (EchoClient echoClient = new EchoClient("localhost", 8080)) {



        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
