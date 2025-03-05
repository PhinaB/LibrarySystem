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
    void testPersonConstructor() {
        // Arrange
        String expectedFirstName = "";
        String expectedLastName = "";
        LocalDate expectedBirthDate = LocalDate.of(2000, 1, 1);
        Address expectedAddress = new Address("street", 2, "12345", "Erfurt", "Germany");

        // Act
        Person testPerson = new Person(expectedFirstName, expectedLastName, expectedBirthDate, expectedAddress);
        Person testPerson2 = new Person("f", "l", LocalDate.of(2000, 1, 1), new Address("other street", 5, "54321", "Erfurt", "Germany"));

        // Assert
        assertEquals(expectedFirstName, testPerson.getFirstName(), "The first name of the person should be set correctly.");
        assertEquals(expectedLastName, testPerson.getLastName(), "The last name of the person should be set correctly.");
        assertEquals(expectedBirthDate, testPerson.getBirthDate(), "The birthdate of the person should be set correctly.");
        assertEquals(expectedAddress, testPerson.getAddress().orElse(null), "The address of the person should be set correctly.");

        assertEquals(testPerson.getId() + 1, testPerson2.getId(), "The ID of the second person should be 1 higher.");
    }

    @Test
    void testPersonConstructorWithoutAddress() {
        // Arrange
        String expectedFirstName = "";
        String expectedLastName = "";
        LocalDate expectedBirthDate = LocalDate.of(2000, 1, 1);

        // Act
        Person testPerson = new Person(expectedFirstName, expectedLastName, expectedBirthDate);
        Person testPerson2 = new Person("f", "l", LocalDate.of(2000, 1, 1));

        // Assert
        assertEquals(expectedFirstName, testPerson.getFirstName(), "The first name of the person should be set correctly.");
        assertEquals(expectedLastName, testPerson.getLastName(), "The last name of the person should be set correctly.");
        assertEquals(expectedBirthDate, testPerson.getBirthDate(), "The birthdate of the person should be set correctly.");

        assertEquals(testPerson.getId() + 1, testPerson2.getId(), "The ID of the second person should be 1 higher.");
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
        // Arrange
        person.getBorrowedBooks().clear();

        // Act
        assertThrows(IllegalArgumentException.class, () -> person.addNewBorrowedBook(null));

        // Assert
        assertTrue(person.getBorrowedBooks().isEmpty(), "The book shouldn't have a borrowed person.");
    }

    @Test
    void testRemoveBorrowedBook() {
        // Arrange
        person.addNewBorrowedBook(book1);

        // Act
        person.removeBorrowedBook(book1);

        // Assert
        assertFalse(person.getBorrowedBooks().contains(book1), "Book may not continue to be included.");
    }

    @Test
    void testRemoveBorrowedBookWhenBookNotInList() {
        // Arrange
        person.addNewBorrowedBook(book1);

        // Act
        person.removeBorrowedBook(book2);

        // Assert
        assertEquals(1, person.getBorrowedBooks().size(), "book2 wurde nie hinzugefügt und kann daher nicht entfernt werden. Es darf kein anderes Buch entfernt werden.");
    }

    @Test
    void testRemoveBorrowedBookWhenBookIsNull() {
        // Arrange
        person.getBorrowedBooks().clear();

        // Act
        assertThrows(IllegalArgumentException.class, () -> person.removeBorrowedBook(null));

        // Assert
        assertTrue(person.getBorrowedBooks().isEmpty(), "The person shouldn't have a borrowed book.");
    }

    @Test
    void testAddFeeNormal() {
        // Act
        person.addFee(10.50);

        // Assert
        assertEquals(10.50, person.getOpenFees(), "The open fee should be 10.50.");
    }

    @Test
    void testAddMultipleFees() {
        // Act
        person.addFee(5.00);
        person.addFee(2.50);

        // Assert
        assertEquals(7.50, person.getOpenFees(), "The open fee should be 7.50.");
    }

    @Test
    void testAddZeroFee() {
        // Act
        person.addFee(0.0);

        // Assert
        assertEquals(0.0, person.getOpenFees(), "The open fee should be 0.0.");
    }

    @Test
    void testAddNegativeFee() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> person.addFee(-5.00),
                "Negative Gebühren sollten nicht erlaubt sein.");
    }
}