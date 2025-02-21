package de.fherfurt.librarySystem;

import de.fherfurt.librarySystem.logic.LibrarySystem;
import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();

        Book book1 = new Book("Titel des Buchs", "Xander", "Komödie", false);
        Book book2 = new Book("Titel des Buchssss", "Zeranski", "Horror", true);

        Person person1 = new Person("Stephanie", "W", LocalDate.of(2000, 2, 2));

        System.out.println(book1);
        System.out.println(book2);
        System.out.println(person1);
    }
}
