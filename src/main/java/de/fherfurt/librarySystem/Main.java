package de.fherfurt.librarySystem;

import de.fherfurt.librarySystem.logic.LibrarySystem;
import de.fherfurt.librarySystem.logic.PersonFilter;
import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();

        Book book1 = new Book("Titel des Buchs", "Xander", "Komödie", false);
        Book book2 = new Book("Titel des Buchssss", "Zeranski", "Horror", true);

        Person person1 = new Person("Stephanie", "W", LocalDate.of(2000, 2, 2));

        Person person2 = new Person("Marie", "K", LocalDate.of(2001,1,1));

        person2.addNewBorrowedBook(book1);
        person2.addNewBorrowedBook(book2);
        librarySystem.addPerson(person1);
        librarySystem.addPerson(person2);


        System.out.println(person1.countBorrowedBooks());

        PersonFilter personFilter = new PersonFilter();
        personFilter.setMinBooksBorrowed(1);
        personFilter.setMaxBooksBorrowed(3);
        List<Person> filterResult = librarySystem.filterPersons(personFilter);

        System.out.println(book1);
        System.out.println(book2);
        System.out.println(person1);
        System.out.println("_________");
        System.out.println(librarySystem.toString());
        System.out.println("_________");
        System.out.println(person2.toString());
        System.out.println("_________");
        System.out.println(filterResult);
    }
}
