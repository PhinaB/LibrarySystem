package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Address;
import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibrarySystemTest {
    private LibrarySystem librarySystem;

    @BeforeEach
    void setUp() {
        // TODO Liste persons & books füllen
        librarySystem = new LibrarySystem();

        Address address1 = new Address("Musterstraße", 1, "99999", "Musterhausen", "Musterland");
        Address address2 = new Address("Mustergasse", 2, "11111", "Mustercity", "Musterland");

        // TODO: wenn jemand hier was ändert, muss das in den Tests auch geändert werden
        librarySystem.addPerson(new Person("VornameEins", "NachnameEins", LocalDate.of(2001, 1, 1), address1));
        librarySystem.addPerson(new Person("VornameZwei", "NachnameZwei", LocalDate.of(2002, 2, 2), address2));
        librarySystem.addPerson(new Person("VornameDrei", "NachnameDrei", LocalDate.of(2003, 3, 3)));

        librarySystem.addBook(new Book("Test Buch Titel1", "Autor Test1", "Testgenre1", false));
        librarySystem.addBook(new Book("Test Buch Titel2", "Autor Test2", "Testgenre2", false));
        librarySystem.addBook(new Book("Test Buch Titel3", "Autor Test3", "Testgenre3", true));
        librarySystem.addBook(new Book("Test Buch Titel4", "Autor Test4", "Testgenre4", false));
        librarySystem.addBook(new Book("Test Buch Titel5", "Autor Test5", "Testgenre5", true));
        librarySystem.addBook(new Book("Test Buch Titel6", "Autor Test6", "Testgenre6", true));
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
    void testEditBookWithNullValuesFilled() {
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
    void testEditBookWithAllNullValuesFilled() {
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

    @Test
        //TODO: eventuell nicht nötig?
    void testEditBookNotExistingInList() {
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
    void testDeleteBookFromEmptyList() {
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
    void testDeleteBookWhileStillBorrowed() {
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
        // Arrange
        Person newPerson = new Person("VornameTest", "NachnameTest", LocalDate.of(2001, 1, 1));

        // Act
        librarySystem.addPerson(newPerson);

        // Assert
        assertTrue(librarySystem.getPersons().contains(newPerson), "Die Person wurde nicht hinzugefügt.");
    }

    @Test
    void testEditPersonWithAllValuesFilled() {
        // Arrange
        Person person = librarySystem.getPersons().get(0);
        int personId = person.getId();

        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        LocalDate newBirthday = LocalDate.of(2011, 11, 11);
        Address newAddress = new Address("Nix mit Muster Straße", 111, "12345", "Erfurt", "Deutschland");

        // Act
        librarySystem.editPerson(personId, newFirstName, newLastName, newBirthday, newAddress);

        // Assert
        assertEquals(newFirstName, person.getFirstName(), "Vorname stimmt nicht überein.");
        assertEquals(newLastName, person.getLastName(), "Nachname stimmt nicht überein.");
        assertEquals(newBirthday, person.getBirthDate(), "Geburtsdatum stimmt nicht überein.");

        assertTrue(person.getAddress().isPresent(), "Adresse sollte vorhanden sein.");
        assertEquals(newAddress.getStreet(), person.getAddress().get().getStreet(), "Straße der Adresse stimmt nicht überein.");
        assertEquals(newAddress.getHouseNumber(), person.getAddress().get().getHouseNumber(), "Hausnummer der Adresse stimmt nicht überein.");
        assertEquals(newAddress.getPostalCode(), person.getAddress().get().getPostalCode(), "PLZ der Adresse stimmt nicht überein.");
        assertEquals(newAddress.getCity(), person.getAddress().get().getCity(), "Stadt der Adresse stimmt nicht überein.");
        assertEquals(newAddress.getCountry(), person.getAddress().get().getCountry(), "Land der Adresse stimmt nicht überein.");
    }

    @Test
    void testEditPersonWithNullValues() {
        // Arrange
        Person testPerson = new Person("Anna", "Beispiel", LocalDate.of(2000, 6, 10), new Address("Hauptstraße", 2, "11111", "Erfurt", "Deutschland"));
        librarySystem.addPerson(testPerson);

        int personId = testPerson.getId();

        String newLastName = "newLastName";

        // Act
        librarySystem.editPerson(personId, null, newLastName, null, null);

        // Assert
        assertEquals("Anna", testPerson.getFirstName(), "Vorname stimmt nicht überein. Es sollte der vorherige Vorname sein.");
        assertEquals(newLastName, testPerson.getLastName(), "Nachname stimmt nicht überein.");
        assertEquals(LocalDate.of(2000, 6, 10), testPerson.getBirthDate(), "Geburtsdatum stimmt nicht überein.");

        assertTrue(testPerson.getAddress().isPresent(), "Adresse sollte vorhanden sein.");
        assertEquals("Hauptstraße", testPerson.getAddress().get().getStreet(), "Straße der Adresse stimmt nicht überein.");
        assertEquals(2, testPerson.getAddress().get().getHouseNumber(), "Hausnummer der Adresse stimmt nicht überein.");
        assertEquals("11111", testPerson.getAddress().get().getPostalCode(), "PLZ der Adresse stimmt nicht überein.");
        assertEquals("Erfurt", testPerson.getAddress().get().getCity(), "Stadt der Adresse stimmt nicht überein.");
        assertEquals("Deutschland", testPerson.getAddress().get().getCountry(), "Land der Adresse stimmt nicht überein.");
    }

    @Test
    void testEditPersonWithAllNullValues() {
        // Arrange
        Person testPerson = new Person("Alice", "Miller", LocalDate.of(1995, 5, 20), new Address("Hauptstraße", 2, "11111", "Erfurt", "Deutschland"));
        librarySystem.addPerson(testPerson);

        int personId = testPerson.getId();

        // Act
        librarySystem.editPerson(personId, null, null, null, null);

        // Assert
        assertEquals("Alice", testPerson.getFirstName(), "Vorname stimmt nicht überein. Es sollte der vorherige Vorname sein.");
        assertEquals("Miller", testPerson.getLastName(), "Nachname stimmt nicht überein.");
        assertEquals(LocalDate.of(1995, 5, 20), testPerson.getBirthDate(), "Geburtsdatum stimmt nicht überein.");

        assertTrue(testPerson.getAddress().isPresent(), "Adresse sollte vorhanden sein.");
        assertEquals("Hauptstraße", testPerson.getAddress().get().getStreet(), "Straße der Adresse stimmt nicht überein.");
        assertEquals(2, testPerson.getAddress().get().getHouseNumber(), "Hausnummer der Adresse stimmt nicht überein.");
        assertEquals("11111", testPerson.getAddress().get().getPostalCode(), "PLZ der Adresse stimmt nicht überein.");
        assertEquals("Erfurt", testPerson.getAddress().get().getCity(), "Stadt der Adresse stimmt nicht überein.");
        assertEquals("Deutschland", testPerson.getAddress().get().getCountry(), "Land der Adresse stimmt nicht überein.");
    }

    @Test
    void testEditPersonWithNonExistentId() {
        // Arrange
        Person person = librarySystem.getPersons().get(0);

        int sizeBefore = librarySystem.getPersons().size();

        // Act
        librarySystem.editPerson(999, "TestVorname", "TestNachname", LocalDate.of(2000, 1, 1), null);

        // Assert
        assertEquals(sizeBefore, librarySystem.getPersons().size(), "Die Anzahl der Personen sollte sich nicht ändern.");
        assertEquals("VornameEins", person.getFirstName(), "Vorname des ersten Eintrags sollte gleich bleiben.");
        assertEquals("NachnameEins", person.getLastName(), "Vorname des ersten Eintrags sollte gleich bleiben.");
        assertEquals(LocalDate.of(2001, 1, 1), person.getBirthDate(), "Geburtstag des ersten Eintrags sollte gleich bleiben.");

        assertTrue(person.getAddress().isPresent(), "Adresse sollte vorhanden sein.");
        assertEquals("Musterstraße", person.getAddress().get().getStreet(), "Straße der Adresse stimmt nicht überein.");
        assertEquals(1, person.getAddress().get().getHouseNumber(), "Hausnummer der Adresse stimmt nicht überein.");
        assertEquals("99999", person.getAddress().get().getPostalCode(), "PLZ der Adresse stimmt nicht überein.");
        assertEquals("Musterhausen", person.getAddress().get().getCity(), "Stadt der Adresse stimmt nicht überein.");
        assertEquals("Musterland", person.getAddress().get().getCountry(), "Land der Adresse stimmt nicht überein.");
    }

    @Test
    void testEditPersonWithEmptyString() {
        // Arrange
        Person testPerson = librarySystem.getPersons().get(0);
        int personId = testPerson.getId();

        // Act
        librarySystem.editPerson(personId, "", "", null, null);

        // Assert
        assertEquals("VornameEins", testPerson.getFirstName(), "Vorname muss gleich bleiben, weil ein leerer String als Vorname nicht erlaubt ist.");
        assertEquals("NachnameEins", testPerson.getLastName(), "Nachname muss gleich bleiben, weil ein leerer String als Nachname nicht erlaubt ist.");
    }

    @Test
    void testDeletePerson() {
        // Arrange
        Person testPerson = librarySystem.getPersons().get(0);

        // Act
        librarySystem.deletePerson(testPerson);

        // Assert
        assertFalse(librarySystem.getPersons().contains(testPerson), "Die Person wurde nicht gelöscht.");
    }

    @Test
    void testBorrowBook() {
        // TODO Lucas
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);

        // Act
        boolean result = librarySystem.borrowBook(book, person);

        // Assert
        assertTrue(result, "Buch sollte ausgeliehen sien");
        assertTrue(book.isBorrowed(), "Buch Status sollte true sein.");
    }

    @Test
    void testGaveBookBack() {
        // TODO Lucas
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        librarySystem.borrowBook(book, person); // Erst ausleihen

        // Act
        boolean result = librarySystem.gaveBookBack(book, person);

        // Assert
        assertTrue(result, "The book should be successfully returned.");
        assertFalse(book.isBorrowed(), "The book's borrowed status should be false.");
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
    void testFilterBooksOnEmptyList() {
        //Arrange
        librarySystem.getBooks().clear();
        //Act
        BookFilter bookFilter = new BookFilter();
        List<Book> filteredBooks = librarySystem.filterBooks(bookFilter);
        //Assert
        assertEquals(0, filteredBooks.size(), "No book should have been filtered becuase no book is in the list.");
    }

    @Test
    void testFilterPersonsWithBooks() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        Book testBook1 = librarySystem.getBooks().get(0);
        Book testBook2 = librarySystem.getBooks().get(1);
        Book testBook3 = librarySystem.getBooks().get(2);

        testPerson1.addNewBorrowedBook(testBook1);
        testPerson2.addNewBorrowedBook(testBook2);
        testPerson3.addNewBorrowedBook(testBook3);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setBooks(Collections.singletonList(testBook1));

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(1, filteredPersons.size(), "Es sollte nur eine Person zurückgegeben werden.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 sollte nicht enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 sollte nicht enthalten sein.");
    }

    @Test
    void testFilterPersonsWithoutBooks() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        Book testBook1 = librarySystem.getBooks().get(0);
        Book testBook2 = librarySystem.getBooks().get(1);
        Book testBook3 = librarySystem.getBooks().get(2);

        testPerson1.addNewBorrowedBook(testBook1);
        testPerson2.addNewBorrowedBook(testBook2);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setBooks(Collections.singletonList(testBook3));

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(0, filteredPersons.size(), "Es sollte keine Person zurückgegeben werden.");
        assertFalse(filteredPersons.contains(testPerson1), "Person 1 sollte nicht enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 sollte nicht enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 sollte nicht enthalten sein.");
    }

    @Test
    void testFilterPersonsWithAllBooks() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Book testBook1 = librarySystem.getBooks().get(0);
        Book testBook2 = librarySystem.getBooks().get(1);

        testPerson1.addNewBorrowedBook(testBook1);
        testPerson2.addNewBorrowedBook(testBook2);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setBooks(Arrays.asList(testBook1, testBook2));

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(2, filteredPersons.size(), "Es sollten zwei Personen zurückgegeben werden.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 sollte enthalten sein.");
    }

    @Test
    void testFilterPersonsWithMinOpenFees() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        testPerson1.addFee(5.0);
        testPerson2.addFee(15.0);
        testPerson3.addFee(2.0);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setMinOpenFees(5.0);

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(2, filteredPersons.size(), "Es sollten nur Personen mit offenen Gebühren >= 5.0 zurückgegeben werden.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 sollte enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 sollte nicht enthalten sein.");
    }

    @Test
    void testFilterPersonsWithMaxOpenFees() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        testPerson1.addFee(5.0);
        testPerson2.addFee(15.0);
        testPerson3.addFee(2.0);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setMaxOpenFees(5.0);

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(2, filteredPersons.size(), "Es sollten nur Personen mit offenen Gebühren <= 5.0 zurückgegeben werden.");
        assertTrue(filteredPersons.contains(testPerson3), "Person 3 sollte enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 sollte nicht enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
    }

    @Test
    void testFilterPersonsWithMinBooks() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        Book testBook1 = librarySystem.getBooks().get(0);
        Book testBook2 = librarySystem.getBooks().get(1);
        Book testBook3 = librarySystem.getBooks().get(2);
        Book testBook4 = librarySystem.getBooks().get(3);
        Book testBook5 = librarySystem.getBooks().get(4);
        Book testBook6 = librarySystem.getBooks().get(5);

        testPerson1.addNewBorrowedBook(testBook1);
        testPerson1.addNewBorrowedBook(testBook2);
        testPerson2.addNewBorrowedBook(testBook3);
        testPerson2.addNewBorrowedBook(testBook4);
        testPerson3.addNewBorrowedBook(testBook5);
        testPerson1.addNewBorrowedBook(testBook6);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setMinBooksBorrowed(2);

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(2, filteredPersons.size(), "Es sollten genau 2 Personen mit mind. 2 Büchern zurückgegeben werden.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 sollte enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 sollte nicht enthalten sein.");
    }

    @Test
    void testFilterPersonsWithMaxBooks() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        Book testBook1 = librarySystem.getBooks().get(0);
        Book testBook2 = librarySystem.getBooks().get(1);
        Book testBook3 = librarySystem.getBooks().get(2);
        Book testBook4 = librarySystem.getBooks().get(3);
        Book testBook5 = librarySystem.getBooks().get(4);
        Book testBook6 = librarySystem.getBooks().get(5);

        testPerson1.addNewBorrowedBook(testBook1);
        testPerson1.addNewBorrowedBook(testBook2);
        testPerson2.addNewBorrowedBook(testBook3);
        testPerson2.addNewBorrowedBook(testBook4);
        testPerson3.addNewBorrowedBook(testBook5);
        testPerson1.addNewBorrowedBook(testBook6);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setMaxBooksBorrowed(2);

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(2, filteredPersons.size(), "Es sollten genau 2 Personen mit max. 2 Büchern zurückgegeben werden.");
        assertFalse(filteredPersons.contains(testPerson1), "Person 1 sollte nicht enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 sollte nicht enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson3), "Person 3 sollte enthalten sein.");
    }

    @Test
    void testFilterPersonsWithMultipleCriteria() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);
        Person testPerson3 = librarySystem.getPersons().get(2);
        Book testBook1 = librarySystem.getBooks().get(0);
        Book testBook2 = librarySystem.getBooks().get(1);
        Book testBook3 = librarySystem.getBooks().get(2);
        Book testBook4 = librarySystem.getBooks().get(3);
        Book testBook5 = librarySystem.getBooks().get(4);
        Book testBook6 = librarySystem.getBooks().get(5);

        testPerson1.addNewBorrowedBook(testBook1);
        testPerson1.addNewBorrowedBook(testBook2);
        testPerson2.addNewBorrowedBook(testBook3);
        testPerson2.addNewBorrowedBook(testBook4);
        testPerson3.addNewBorrowedBook(testBook5);
        testPerson1.addNewBorrowedBook(testBook6);

        testPerson1.addFee(5.0);
        testPerson2.addFee(15.0);
        testPerson3.addFee(2.0);

        // Act
        PersonFilter personFilter = new PersonFilter();
        personFilter.setBooks(Arrays.asList(testBook1, testBook2, testBook5));
        personFilter.setMaxOpenFees(6.0);
        personFilter.setMinOpenFees(3.0);

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(1, filteredPersons.size(), "Es sollte genau 1 Person mit diesen Büchern und diesen Gebühren zurückgegeben werden.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 sollte nicht enthalten sein.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 sollte nicht enthalten sein.");
    }

    @Test
    void testFilterPersonsWithNoCriteria() {
        // Arrange
        Person testPerson1 = librarySystem.getPersons().get(0);
        Person testPerson2 = librarySystem.getPersons().get(1);

        // Act
        PersonFilter personFilter = new PersonFilter();

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(3, filteredPersons.size(), "Es sollte alle Personen zurückgegeben werden, die in dem LibrarySystem enthalten sind.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 sollte enthalten sein.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 sollte enthalten sein.");
    }

    @Test
    void testFilterPersonsWithEmptyData() {
        // Arrange
        librarySystem.getPersons().clear();

        // Act
        PersonFilter personFilter = new PersonFilter();

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(0, filteredPersons.size(), "Es sollte keine Person zurückgegeben werden.");
    }

    @Test
    void testCalculateFeeForBook() {
        // TODO Lucas
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        librarySystem.borrowBook(book, person);

        LocalDate dueDate = LocalDate.now().minusDays(10);

        // Act
        double fee = librarySystem.calculateFeeForBook(book, dueDate);

        // Assert
        assertEquals(10 * 0.50, fee, 0.01, "The fee should be 0.50 per day overdue.");
    }
}