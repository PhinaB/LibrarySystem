package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibrarySystemTest {
    private LibrarySystem librarySystem;

    @BeforeEach
    void setUp() {
        // TODO Liste persons & books füllen
        librarySystem = new LibrarySystem();

        librarySystem.addPerson(new Person("VornameEins", "NachnameEins", LocalDate.of(2001, 1, 1)));
        librarySystem.addPerson(new Person("VornameZwei", "NachnameZwei", LocalDate.of(2002, 2, 2)));
        librarySystem.addPerson(new Person("VornameDrei", "NachnameDrei", LocalDate.of(2003, 3, 3)));

        librarySystem.addBook(new Book("Test Buch Titel1", "Autor Test1", "Testgenre1", false));
        librarySystem.addBook(new Book("Test Buch Titel2", "Autor Test2", "Testgenre2", false));
        librarySystem.addBook(new Book("Test Buch Titel3", "Autor Test3", "Testgenre3", true));
    }

    @AfterEach
    void tearDown() {
        // TODO
        librarySystem = null;
    }

    @Test
    void testAddBook() {
        // TODO Josephina
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testEditBook() {
        // TODO Josephina
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testDeleteBook() {
        // TODO Josephina
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testAddPerson() {
        // TODO Stephanie
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testEditPerson() {
        // TODO Stephanie
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testDeletePerson() {
        // TODO Stephanie
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testBorrowBook() {
        // TODO Lucas
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testGaveBookBack() {
        // TODO Lucas
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testFilterBooks() {
        // TODO Josephina
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testFilterPersons() {
        // TODO Stephanie
        // Arrange

        // Act

        // Assert
    }

    @Test
    void testCalculateFeeForBook() {
        // TODO Lucas
        // Arrange

        // Act

        // Assert
    }
}