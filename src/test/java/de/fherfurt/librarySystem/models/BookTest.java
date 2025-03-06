package de.fherfurt.librarySystem.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;
    private Person person;

    @BeforeEach
    void setUp() {
        book = new Book("Testbuch", "Max Mustermann", "Roman", false);
        person = new Person("Lisa", "Müller", LocalDate.of(1995, 5, 15));
    }

    @AfterEach
    void tearDown() {
        person = null;
        book = null;
    }

    @Test
    void testBookConstructor() {
        // Arrange
        String expectedTitle = "Harry Potter";
        String expectedAuthor = "J.K. Rowling";
        String expectedGenre = "Fantasy";
        boolean expectedIsDamaged = false;

        // Act
        Book testBook = new Book(expectedTitle, expectedAuthor, expectedGenre, expectedIsDamaged);
        Book anotherBook = new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", false);

        // Assert
        assertEquals(expectedTitle, testBook.getTitle(), "The title of the book should be set correctly.");
        assertEquals(expectedAuthor, testBook.getAuthor(), "The author of the book should be set correctly.");
        assertEquals(expectedGenre, testBook.getGenre(), "The genre of the book should be set correctly.");
        assertEquals(expectedIsDamaged, testBook.isDamaged(), "The damage status of the book should be set correctly.");

        assertEquals(testBook.getId() + 1, anotherBook.getId(), "The ID of the second book should be 1 higher.");
    }

    @Test
    void testSetBorrowWithoutSpecificDate() {
        // Arrange
        LocalDate currentDate = LocalDate.now();

        // Act
        book.setBorrow(person);

        // Assert
        assertTrue(book.getPersonBorrowed().isPresent(), "The book should have a borrowed person.");
        assertEquals(person, book.getPersonBorrowed().get(), "The person on loan is not correct.");
        assertTrue(book.getBorrowDate().isPresent(), "The loan date should be set.");
        assertEquals(currentDate, book.getBorrowDate().get(), "The borrowing date should correspond to the current date.");
    }

    @Test
    void testSetBorrowWithSpecificDate() {
        // Arrange
        LocalDate specificDate = LocalDate.of(2025, 5, 1);

        // Act
        book.setBorrow(person, specificDate);

        // Assert
        assertTrue(book.getPersonBorrowed().isPresent(), "The book should have a borrowed person.");
        assertEquals(person, book.getPersonBorrowed().get(), "The person on loan is not correct.");
        assertTrue(book.getBorrowDate().isPresent(), "The loan date should be set.");
        assertEquals(specificDate, book.getBorrowDate().get(), "The borrowing date should correspond to the specific date.");
    }

    @Test
    void testSetBorrowWhenBookAlreadyBorrowed() {
        // Arrange
        Person secondPerson = new Person("Jane", "Doe", LocalDate.of(1995, 5, 15));

        // Act
        book.setBorrow(person);
        book.setBorrow(secondPerson);

        // Assert
        assertTrue(book.getPersonBorrowed().isPresent(), "The book should have a borrowed person.");
        assertEquals(person, book.getPersonBorrowed().get(), "The person borrowed should be the first person.");
        assertNotEquals(secondPerson, book.getPersonBorrowed().get(), "The book should not be loaned to the second person.");
    }


    @Test
    void testSetBorrowWhenBookAlreadyBorrowedAndDateChanges() {
        // Arrange
        LocalDate firstDate = LocalDate.of(2025, 1, 1);
        LocalDate secondDate = LocalDate.of(2025, 5, 1);

        // Act
        book.setBorrow(person, firstDate);
        book.setBorrow(person, secondDate);

        // Assert
        assertTrue(book.getPersonBorrowed().isPresent(), "The book should have a borrowed person.");
        assertEquals(person, book.getPersonBorrowed().get(), "The person on loan is not correct.");
        assertEquals(firstDate, book.getBorrowDate().orElse(null), "The borrowing date should be the first date.");
    }

    @Test
    void testSetBorrowForNullPerson() {
        // Act
        book.setBorrow(null, LocalDate.now());

        // Assert
        assertFalse(book.getPersonBorrowed().isPresent(), "The book shouldn't have a borrowed person.");
    }

    @Test
    void testRemoveBorrow() {
        // Arrange
        book.setBorrow(person, LocalDate.now());

        // Act
        book.removeBorrow();

        // Assert
        assertFalse(book.isBorrowed(), "The book should no longer be marked as borrowed.");
        assertNull(book.getPersonBorrowed().orElse(null), "There should no longer be a person assigned.");
        assertNull(book.getBorrowDate().orElse(null), "The borrow date should be zero.");
    }

    @Test
    void testRemoveBorrowWhenNotBorrowed() {
        // Act
        book.removeBorrow();

        // Assert
        assertFalse(book.isBorrowed(), "The book should not be marked as borrowed.");
        assertNull(book.getPersonBorrowed().orElse(null), "There should no longer be a person assigned.");
        assertNull(book.getBorrowDate().orElse(null), "The borrow date should be zero.");
    }

    @Test
    void testSetDamagedWhenNotDamaged() {
        // Act
        book.setDamaged(true);

        // Assert
        assertTrue(book.isDamaged(), "The book should be marked as damaged.");
    }

    @Test
    void testSetDamagedWhenAlreadyDamaged() {
        // Arrange
        Book book = new Book("Title", "Author", "Genre", true);

        // Act
        book.setDamaged(true);

        // Assert
        assertTrue(book.isDamaged(), "The book should still be marked as damaged.");
    }

    @Test
    void testSetDamagedWhenAlreadyDamagedAndSetFalse() {
        // Arrange
        Book book = new Book("Title", "Author", "Genre", true);

        // Act
        book.setDamaged(false);

        // Assert
        assertTrue(book.isDamaged(), "The book should still be marked as damaged because it is already damaged.");
    }
}
