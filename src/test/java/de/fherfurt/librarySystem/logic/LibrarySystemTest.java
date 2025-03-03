package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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
        librarySystem.getBooks().clear();
        Book book = new Book("Title1", "Author1", "Genre1", false);
        int arrayListSizeBeforeAdd = librarySystem.getBooks().size();

        // Act
        Book resultBookAdded = librarySystem.addBook(book);
        Book resultBookAddedAnotherTime = librarySystem.addBook(book);
        Book resultEmptyBookAdded = librarySystem.addBook(null);

        // Assert
        assertNotNull(resultBookAdded, "Book should be added and it should be returned.");
        assertNull(resultBookAddedAnotherTime, "Book already exists in List and should not be added.");
        assertNull(resultEmptyBookAdded, "Book should not be added and null should be returned.");
        assertEquals(0, arrayListSizeBeforeAdd, "Test should start with an empty ArrayList.");
        assertEquals(arrayListSizeBeforeAdd + 1, librarySystem.getBooks().size(), "One book was added, and should be counted.");

        Book fetchedBook = librarySystem.getBooks().get(0);
        assertNotNull(fetchedBook, "A book should be found at the first index.");
        assertEquals(book, fetchedBook, "Added book should be at first index.");
    }

    @Test
    void testEditBookWithAllValuesFilled() {
        // TODO Josephina
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        int bookId = book.getId();

        String newTitle = "new Title1";
        String newAuthor = "new Author1";
        String newGenre = "new Genre1";

        // Act
        librarySystem.editBook(bookId, newTitle, newAuthor, newGenre);

        // Assert
        assertEquals(newTitle, book.getTitle(), "Book should have new title.");
        assertEquals(newAuthor, book.getAuthor(), "Book should have new author.");
        assertEquals(newGenre, book.getGenre(), "Book should have new genre.");
    }

    @Test
    void testEditBookWithNullValuesFilled(){
        //Arrange
        Book book = librarySystem.getBooks().get(0);
        int bookId = book.getId();

        String newTitle = "new Title1";
        String newGenre = "new Genre1";

        //Act
        boolean resultBookEdited = librarySystem.editBook(bookId, newTitle, null, newGenre);

        //Assert
        assertTrue(resultBookEdited, "Book should be edited.");
        assertEquals(newTitle, book.getTitle(), "Book should have new title.");
        assertEquals("Autor Test1", book.getAuthor(), "Book should have same author as before.");
        assertEquals(newGenre, book.getGenre(), "Book should have new genre.");
    }
    @Test
    void testEditBookWithAllNullValuesFilled(){
        //Arrange
        Book book = librarySystem.getBooks().get(0);
        int bookId = book.getId();

        //Act
        boolean resultBookEdited = librarySystem.editBook(bookId, null, null, null);

        //Assert
        assertTrue(resultBookEdited, "Book should be edited but with no values changed.");//TODO: Kommt auf die Implementierung an
        assertEquals("Test Buch Titel1", book.getTitle(), "Book should have same title as before.");
        assertEquals("Autor Test1", book.getAuthor(), "Book should have same author as before.");
        assertEquals("Testgenre1", book.getGenre(), "Book should have same genre as before.");
    }

    @Test //TODO: eventuell nicht nötig?
    void testEditBookNotExistingInList(){
        //Arrange
        Book book = new Book("Title4", "Author4", "Genre4", false);
        int bookId = book.getId();

        String newTitle = "new Title4";
        String newAuthor = "new Author4";
        String newGenre = "new Genre4";
        //Act
        boolean resultBookEdited = librarySystem.editBook(bookId, newTitle, newAuthor, newGenre);

        //Assert
        assertFalse(resultBookEdited, "Book should not be edited because it is not in the list.");
        assertNotEquals(newTitle, book.getTitle(), "Book Title should not have changed.");
        assertNotEquals(newAuthor, book.getAuthor(), "Book Title should not have changed.");
        assertNotEquals(newGenre, book.getGenre(), "Genre Title should not have changed.");
    }


    @Test
    void testDeleteBook() {
        // TODO Josephina
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        int arrayListSizeBeforeDelete = librarySystem.getBooks().size();
        // Act
        librarySystem.deleteBook(book);
        librarySystem.deleteBook(null); //TODO: ist es okay zwei verschiedene Dinge in einem Test zu testen? Hatte Xander in seinem testAddPerson() auch

        // Assert
        assertEquals(arrayListSizeBeforeDelete - 1, librarySystem.getBooks().size(), "One book was removed, so amount should be one lower that before.");
        boolean isRemovedBookFound = librarySystem.getBooks().contains(book);
        assertFalse(isRemovedBookFound, "Book should not be in the ArrayList.");
    }

    @Test
    void testDeleteBookFromEmptyList(){
        //Arrange
        librarySystem.getBooks().clear();
        Book book = new Book("Title1", "Author1", "Genre1", false);
        int arrayListSizeBeforeDelete = librarySystem.getBooks().size();

        //Act
        librarySystem.deleteBook(book);

        //Assert
        assertEquals(0, arrayListSizeBeforeDelete, "ArrayList should be empty before removing.");
        assertEquals(0, librarySystem.getBooks().size(), "Amount of Array should not have changed since ArrayList was empty from the start.");
        boolean isRemovedBookFound = librarySystem.getBooks().contains(book);
        assertFalse(isRemovedBookFound, "Book should not be in the ArrayList.");
    }

    @Test
    void testDeleteBookWhileStillBorrowed(){
        //Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        boolean bookBorrowed = librarySystem.borrowBook(book, person);
        int arrayListSizeBeforeDelete = librarySystem.getBooks().size();

        //Act
        librarySystem.deleteBook(book);

        //Assert
        assertTrue(bookBorrowed, "Borrowing process should be successful.");
        assertEquals(arrayListSizeBeforeDelete, librarySystem.getBooks().size(), "ArrayList should still have the same size.");
        assertTrue(librarySystem.getBooks().contains(book), "Book should be in ArrayList because it is still borrowed and connot be deleted.");
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
    void testFilterBooksWithGenre() {
        // TODO Josephina
        // Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        Book book4 = new Book("Titel4", "Author4", "Testgenre1", false);
        // Act
        BookFilter bookFilter = new BookFilter();
        bookFilter.setGenre(book1.getGenre());
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        // Assert
        assertEquals(2, filteredBooks.size(), "Two books should have been filtered.");
        assertTrue(filteredBooks.contains(book1), "Book1 should have been filtered.");
        assertFalse(filteredBooks.contains(book2), "Book2 should not have been filtered.");
        assertFalse(filteredBooks.contains(book3), "Book3 should not have been filtered.");
        assertTrue(filteredBooks.contains(book4), "Book4 should not have been filtered.");
    }

    @Test
    void testFilterBooksWithAuthor() {
        // TODO Josephina
        // Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        Book book4 = new Book("Title4", "Autor Test2", "Genre4", false);
        // Act
        BookFilter bookFilter = new BookFilter();
        bookFilter.setAuthor(book2.getAuthor());
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        // Assert
        assertEquals(2, filteredBooks.size(), "Two books should have been filtered.");
        assertFalse(filteredBooks.contains(book1), "Book1 should not have been filtered.");
        assertTrue(filteredBooks.contains(book2), "Book2 should have been filtered.");
        assertFalse(filteredBooks.contains(book3), "Book3 should not have been filtered.");
        assertTrue(filteredBooks.contains(book4), "Book4 should have been filtered.");
    }

    @Test
    void testFilterBooksWithTitleAsTextModule() {
        //Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        int numberOfBooks = librarySystem.getBooks().size();
        //Act
        BookFilter bookFilter = new BookFilter();
        bookFilter.setTitle("Ti");
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        //Assert
        assertEquals(numberOfBooks, filteredBooks.size(), "All books should have been filtered.");
        assertTrue(filteredBooks.contains(book1), "Book1 should have been filtered.");
        assertTrue(filteredBooks.contains(book2), "Book2 should have been filtered.");
        assertTrue(filteredBooks.contains(book3), "Book3 should have been filtered.");
    }

    @Test
    void testFilterBooksWithTitleAsWholeTitle() {
        //Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        //Act
        BookFilter bookFilter = new BookFilter();
        bookFilter.setTitle("Test Buch Titel1");
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        //Assert
        assertEquals(1, filteredBooks.size(), "All books should have been filtered.");
        assertTrue(filteredBooks.contains(book1), "Book1 should have been filtered.");
        assertFalse(filteredBooks.contains(book2), "Book2 should not have been filtered.");
        assertFalse(filteredBooks.contains(book3), "Book3 should not have been filtered.");
    }

    @Test
    void testFilterBooksWithMultipleCriteria() {
        //Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        Book book4 = new Book("Title4", "Autor Test2", "Testgenre2", false);
        //Act
        BookFilter bookFilter = new BookFilter();
        bookFilter.setTitle("Ti");
        bookFilter.setGenre(book2.getGenre());
        bookFilter.setAuthor(book2.getAuthor());
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        //Assert
        assertEquals(2, filteredBooks.size(), "Two books should have been filtered.");
        assertTrue(filteredBooks.contains(book2), "Book2 should have been filtered.");
        assertTrue(filteredBooks.contains(book4), "Book4 should have been filtered.");
        assertFalse(filteredBooks.contains(book1), "Book1 should not have been filtered.");
        assertFalse(filteredBooks.contains(book3), "Book3 should not have been filtered.");
    }

    @Test
    void testFilterBooksWithNoCriteria() {
        // TODO Josephina
        // Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        int numberOfBooks = librarySystem.getBooks().size();
        // Act
        BookFilter bookFilter = new BookFilter();
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        //Assert
        assertEquals(numberOfBooks, filteredBooks.size(), "All books should be filtered since there is no filter.");
        assertTrue(filteredBooks.contains(book1), "Book1 should have been filtered.");
        assertTrue(filteredBooks.contains(book2), "Book2 should have been filtered.");
        assertTrue(filteredBooks.contains(book3), "Book3 should have been filtered.");
    }

    @Test
    void testFilterBooksOnEmptyList(){
        //Arrange
        librarySystem.getBooks().clear();
        //Act
        BookFilter bookFilter = new BookFilter();
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        //Assert
        assertEquals(0, filteredBooks.size(), "No book should have been filtered becuase no book is in the list.");
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