package de.fherfurt.librarySystem.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;
    private Person person;

    @BeforeEach
    void setUp() {
        book = new Book("Testbuch", "Max Mustermann", "Roman", false);
        person = new Person("Lisa", "Müller", LocalDate.of(1995, 5, 15));
    }

    // TODO: compareTo, toString testen

    @Test
    void testBookInitialization() {
        assertEquals("Testbuch", book.getTitle());
        assertEquals("Max Mustermann", book.getAuthor());
        assertEquals("Roman", book.getGenre());
        assertFalse(book.isDamaged());
    }

    @Test
    void testBorrowBook() {
        book.setBorrow(person);

        assertTrue(book.getPersonBorrowed().isPresent());
        assertEquals(person, book.getPersonBorrowed().get());
        assertTrue(book.getBorrowDate().isPresent());
    }

    @Test
    void testReturnBook() {
        book.setBorrow(person);
        book.removeBorrow();

        assertFalse(book.getPersonBorrowed().isPresent());
        assertFalse(book.getBorrowDate().isPresent());
    }

    @Test
    void testEqualsAndHashCode() {
        Book sameBook = new Book("Testbuch", "Max Mustermann", "Roman", false);
        assertNotEquals(book, sameBook); // IDs sind unterschiedlich
        assertNotEquals(book.hashCode(), sameBook.hashCode());
    }
}
