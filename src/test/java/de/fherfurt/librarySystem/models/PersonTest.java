package de.fherfurt.librarySystem.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    private Person person;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        person = new Person("Anna", "Test", LocalDate.of(2000, 1, 1));
        book1 = new Book("Test", "Autor Test", "Bestes Genre", false);
        book2 = new Book("Test", "Autor Test", "Bestes Genre", false);
    }

    @AfterEach
    void tearDown() {
        person = null;
        book1 = null;
        book2 = null;
    }

    @Test
    void testAddNewBorrowedBook() {
        // Act
        person.addNewBorrowedBook(book1);

        // Assert
        assertTrue(person.getBorrowedBooks().contains(book1));
    }

    @Test
    void testAddNewBorrowedBookNoDuplicate() {
        // Act
        person.addNewBorrowedBook(book1);
        person.addNewBorrowedBook(book1);

        // Assert
        assertTrue(person.getBorrowedBooks().contains(book1));
    }

    @Test
    void testAddNewBorrowedBookWhenBookIsNull() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> person.addNewBorrowedBook(null));
        assertEquals("Das übergebene Buch darf nicht null sein!", exception.getMessage());
    }

    @Test
    void testRemoveBorrowedBook() {
        // Act
        person.addNewBorrowedBook(book1);
        person.removeBorrowedBook(book1);

        // Assert
        assertFalse(person.getBorrowedBooks().contains(book1), "Buch darf nicht weiterhin enthalten sein.");
    }

    @Test
    void testRemoveBorrowedBookWhenBookNotInList() {
        // Act
        person.addNewBorrowedBook(book1);
        person.removeBorrowedBook(book2);

        // Assert
        assertEquals(1, person.getBorrowedBooks().size(), "book2 wurde nie hinzugefügt und kann daher nicht entfernt werden. Es darf kein anderes Buch entfernt werden.");
    }

    @Test
    void testRemoveBorrowedBookWhenBookIsNull() {
        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> person.removeBorrowedBook(null));
        assertEquals("Das übergebene Buch darf nicht null sein!", exception.getMessage());
    }
}