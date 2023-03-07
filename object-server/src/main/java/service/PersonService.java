package service;

import model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPersons();

    void addPerson(Person person);



}
