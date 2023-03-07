import objecrserver.ObjectServer;
import service.PersonInMemoryService;
import service.PersonService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PersonService personService = new PersonInMemoryService();
        try (ObjectServer objectServer = new ObjectServer(8000, personService)) {




        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
