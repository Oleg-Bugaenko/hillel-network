package objecrserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Address;
import model.Person;
import service.PersonService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ObjectServer implements AutoCloseable{
    private ServerSocket serverSocket;
    private PersonService service;

    public ObjectServer(int port, PersonService personService) throws IOException {
        serverSocket = new ServerSocket(port);
        service = personService;

        while (true) {
            Socket clientSocket = serverSocket.accept();

            new Thread(() -> {
                try (OutputStream outputStream = clientSocket.getOutputStream();
                     PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

                     InputStream inputStream = clientSocket.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
                ) {

                    writer.println("Commands: ");
                    writer.println("-get-all : get all persons");
                    writer.println("-add : add person");
                    writer.println("-add-json : add person json format");
                    writer.flush();

                    String command;
                    do {
                        command = reader.readLine();

                        if (command.equalsIgnoreCase("-get-all")) {
                            List<Person> personList = personService.getPersons();                   //отримання даних

                            ObjectMapper objectMapper = new ObjectMapper();                         //створення мапера для перетворення об'єкту в Json формат
                            String personsJson = objectMapper.writeValueAsString(personList);       //отримання об'єкту у вигляді рядка

                            writer.println(personsJson);                                            //запис даних
                            writer.flush();
                        }

                        if (command.equalsIgnoreCase("-add")) {                         //додаємо нові дані користувача
                            Person person = addPerson(reader, writer);
                            personService.addPerson(person);
                        }

                        if (command.equalsIgnoreCase("-add-json")){
                            writer.println("Enter person in json format");
                            writer.flush();

                            String personsJson = reader.readLine();
                            Person person = new ObjectMapper().readValue(personsJson, Person.class);
                            personService.addPerson(person);
                        }


                    } while (!command.equalsIgnoreCase("-exit"));

                    writer.println("Connection closed");
                    writer.flush();
                    
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();
        }

    }


    private Person addPerson(BufferedReader reader, PrintWriter writer) {
        Address address = new Address();
        Person person = new Person();
        String[] questions = {"Enter name", "Enter age", "Enter city", "Enter street",
        "Enter building number", "Enter zip code"};
        try {
            for (int i = 0; i < questions.length; i++) {
                writer.write(String.format("%s :\n", questions[i]));
                writer.flush();
                String answer = reader.readLine();

                switch (i) {
                    case 0 -> person.setName(answer);
                    case 1 -> person.setAge(Integer.parseInt(answer));
                    case 2 -> address.setCity(answer);
                    case 3 -> address.setStreet(answer);
                    case 4 -> address.setBuildNumber(Integer.parseInt(answer));
                    case 5 -> address.setZipCode(Integer.parseInt(answer));
                }
            }
            person.setAddress(address);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    @Override
    public void close() throws Exception {
        if (!serverSocket.isClosed()) serverSocket.close();
    }
}
