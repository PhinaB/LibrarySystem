package de.fherfurt.librarySystem;

import de.fherfurt.librarySystem.logic.LibrarySystem;
import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();

        Book book1 = new Book("Title of book", "x", "comedy", false);
        Book book2 = new Book("another title of book", "z", "horror", true);

        Person person1 = new Person("Hans", "W", LocalDate.of(2000, 2, 2));
        Person person2 = new Person("Marie", "K", LocalDate.of(2001,1,1));

        System.out.println(librarySystem);

        librarySystem.addPerson(person1);
        librarySystem.addPerson(person2);
        librarySystem.addBook(book1);
        librarySystem.addBook(book2);

        System.out.println(librarySystem);

    }
}
