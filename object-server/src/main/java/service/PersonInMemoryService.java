package service;

import model.Address;
import model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonInMemoryService implements PersonService {
    private List<Person> list = new ArrayList<>() {{
        Address address = new Address("Odessa", "Preobrazhenska", 12, 65001);
        Person person = new Person("John", 34, address);
        add(person);
    }};

    @Override
    public List<Person> getPersons() {
        return list;
    }

    @Override
    public void addPerson(Person person) {
        list.add(person);
    }


}
