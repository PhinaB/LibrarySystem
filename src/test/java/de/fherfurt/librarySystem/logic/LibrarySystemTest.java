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
        librarySystem = new LibrarySystem();

        Address address1 = new Address("model street", 1, "99999", "Model", "Modelland");
        Address address2 = new Address("model alley", 2, "11111", "Modelcity", "Modelland");

        librarySystem.addPerson(new Person("firstNameOne", "lastNameOne", LocalDate.of(2001, 1, 1), address1));
        librarySystem.addPerson(new Person("firstNameTwo", "lastNameTwo", LocalDate.of(2002, 2, 2), address2));
        librarySystem.addPerson(new Person("firstNameThree", "lastNameThree", LocalDate.of(2003, 3, 3)));

        librarySystem.addBook(new Book("Test book title1", "Author Test1", "Testgenre1", false));
        librarySystem.addBook(new Book("Test book title2", "Author Test2", "Testgenre2", false));
        librarySystem.addBook(new Book("Test book title3", "Author Test3", "Testgenre3", true));
        librarySystem.addBook(new Book("Test book title4", "Author Test4", "Testgenre4", false));
        librarySystem.addBook(new Book("Test book title5", "Author Test5", "Testgenre5", true));
        librarySystem.addBook(new Book("Test book title6", "Author Test6", "Testgenre6", true));
    }

    @AfterEach
    void tearDown() {
        librarySystem = null;
    }

    @Test
    void testAddBook() {
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
        assertEquals("Author Test1", book.getAuthor(), "Book should have same author as before.");
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
        assertTrue(resultBookEdited, "Book should be edited but with no values changed.");
        assertEquals("Test book title1", book.getTitle(), "Book should have same title as before.");
        assertEquals("Author Test1", book.getAuthor(), "Book should have same author as before.");
        assertEquals("Testgenre1", book.getGenre(), "Book should have same genre as before.");
    }

    @Test
    void testEditBookWithEmptyString(){
        //Arrange
        Book book = librarySystem.getBooks().get(0);
        int bookId = book.getId();
        //Act
        boolean resultBookEdited = librarySystem.editBook(bookId, "", "author1", "");
        //Assert
        assertTrue(resultBookEdited, "Book should be edited.");
        assertEquals("Test book title1", book.getTitle(), "Book should have same title as before.");
        assertEquals("author1", book.getAuthor(), "Book should have different author than before.");
        assertEquals("Testgenre1", book.getGenre(), "Book should have same genre as before.");
    }

    @Test
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
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        int arrayListSizeBeforeDelete = librarySystem.getBooks().size();

        // Act
        librarySystem.deleteBook(book);
        librarySystem.deleteBook(null);

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
        librarySystem.getPersons().clear();
        Person person = new Person("nameTest", "secondNameTest", LocalDate.of(2001, 1, 1));
        int listSizeBeforeAdd = librarySystem.getPersons().size();
        // Act
        Person resultPersonAdded = librarySystem.addPerson(person);
        Person resultPersonAddedAnotherTime = librarySystem.addPerson(person);
        Person resultEmptyPersonAdded = librarySystem.addPerson(null);

        // Assert
        assertNotNull(resultPersonAdded, "Person should be added and should be returned.");
        assertNull(resultPersonAddedAnotherTime, "Person already exists in  list ans should not be added.");
        assertNull(resultEmptyPersonAdded, "Person is null, should not be added and null should be returned.");
        assertEquals(0, listSizeBeforeAdd, "Test should start with an empty list.");
        assertEquals(listSizeBeforeAdd + 1, librarySystem.getPersons().size(), "One person was added and should be counted.");

        assertTrue(librarySystem.getPersons().contains(person), "The person should be added.");

        Person fetchedPerson = librarySystem.getPersons().get(0);
        assertNotNull(fetchedPerson, "A person should be found at the first index");
        assertEquals(person, fetchedPerson, "Added person should be at first index.");

    }

    // TODO: hinzufügen/bearbeiten von ungültigen Daten: Person gibt ein Geburtsdatum in der Zukunft an

    @Test
    void testEditPersonWithAllValuesFilled() {
        // Arrange
        Person person = librarySystem.getPersons().get(0);
        int personId = person.getId();

        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        LocalDate newBirthday = LocalDate.of(2011, 11, 11);
        Address newAddress = new Address("new street name", 111, "12345", "Erfurt", "germany");

        // Act
        librarySystem.editPerson(personId, newFirstName, newLastName, newBirthday, newAddress);

        // Assert
        assertEquals(newFirstName, person.getFirstName(), "first name does not match.");
        assertEquals(newLastName, person.getLastName(), "last name does not match.");
        assertEquals(newBirthday, person.getBirthDate(), "birthdate does not match.");

        assertTrue(person.getAddress().isPresent(), "Address should be present.");
        assertEquals(newAddress.getStreet(), person.getAddress().get().getStreet(), "street of address does not match.");
        assertEquals(newAddress.getHouseNumber(), person.getAddress().get().getHouseNumber(), "house number of address does not match.");
        assertEquals(newAddress.getPostalCode(), person.getAddress().get().getPostalCode(), "PLZ of address does not match.");
        assertEquals(newAddress.getCity(), person.getAddress().get().getCity(), "city of address does not match.");
        assertEquals(newAddress.getCountry(), person.getAddress().get().getCountry(), "country of address does not match.");
    }

    @Test
    void testEditPersonWithNullValues() {
        // Arrange
        Person testPerson = new Person("Anna", "example", LocalDate.of(2000, 6, 10), new Address("Main Street", 2, "11111", "Erfurt", "Germany"));
        librarySystem.addPerson(testPerson);

        int personId = testPerson.getId();

        String newLastName = "newLastName";

        // Act
        librarySystem.editPerson(personId, null, newLastName, null, null);

        // Assert
        assertEquals("Anna", testPerson.getFirstName(), "first name does not match.");
        assertEquals(newLastName, testPerson.getLastName(), "last name does not match.");
        assertEquals(LocalDate.of(2000, 6, 10), testPerson.getBirthDate(), "birthday does not match.");

        assertTrue(testPerson.getAddress().isPresent(), "Address should be present.");
        assertEquals("Main Street", testPerson.getAddress().get().getStreet(), "street of address does not match.");
        assertEquals(2, testPerson.getAddress().get().getHouseNumber(), "house number of address does not match.");
        assertEquals("11111", testPerson.getAddress().get().getPostalCode(), "PLZ of address does not match.");
        assertEquals("Erfurt", testPerson.getAddress().get().getCity(), "city of address does not match.");
        assertEquals("Germany", testPerson.getAddress().get().getCountry(), "country of address does not match.");
    }

    @Test
    void testEditPersonWithAllNullValues() {
        // Arrange
        Person testPerson = new Person("Alice", "Miller", LocalDate.of(1995, 5, 20), new Address("Main Street", 2, "11111", "Erfurt", "Germany"));
        librarySystem.addPerson(testPerson);

        int personId = testPerson.getId();

        // Act
        librarySystem.editPerson(personId, null, null, null, null);

        // Assert
        assertEquals("Alice", testPerson.getFirstName(), "first name does not match.");
        assertEquals("Miller", testPerson.getLastName(), "last name does not match.");
        assertEquals(LocalDate.of(1995, 5, 20), testPerson.getBirthDate(), "birthday does not match.");

        assertTrue(testPerson.getAddress().isPresent(), "Address should be present.");
        assertEquals("Main Street", testPerson.getAddress().get().getStreet(), "street of address does not match.");
        assertEquals(2, testPerson.getAddress().get().getHouseNumber(), "house number of address does not match.");
        assertEquals("11111", testPerson.getAddress().get().getPostalCode(), "PLZ of address does not match.");
        assertEquals("Erfurt", testPerson.getAddress().get().getCity(), "city of address does not match.");
        assertEquals("Germany", testPerson.getAddress().get().getCountry(), "country of address does not match.");
    }

    @Test
    void testEditPersonWithNonExistentId() {
        // Arrange
        Person person = librarySystem.getPersons().get(0);

        int sizeBefore = librarySystem.getPersons().size();

        // Act
        librarySystem.editPerson(999, "TestFirstName", "TestLastName", LocalDate.of(2000, 1, 1), null);

        // Assert
        assertEquals(sizeBefore, librarySystem.getPersons().size(), "The number of people should not change.");
        assertEquals("firstNameOne", person.getFirstName(), "The first name of the first entry should remain the same.");
        assertEquals("lastNameOne", person.getLastName(), "The last name of the first entry should remain the same.");
        assertEquals(LocalDate.of(2001, 1, 1), person.getBirthDate(), "Birthdate of the first entry should remain the same.");

        assertTrue(person.getAddress().isPresent(), "Address should be present.");
        assertEquals("model street", person.getAddress().get().getStreet(), "street of address does not match.");
        assertEquals(1, person.getAddress().get().getHouseNumber(), "house number of address does not match.");
        assertEquals("99999", person.getAddress().get().getPostalCode(), "PLZ of address does not match.");
        assertEquals("Model", person.getAddress().get().getCity(), "city of address does not match.");
        assertEquals("Modelland", person.getAddress().get().getCountry(), "country of address does not match.");
    }

    @Test
    void testEditPersonWithEmptyString() {
        // Arrange
        Person testPerson = librarySystem.getPersons().get(0);
        int personId = testPerson.getId();

        // Act
        librarySystem.editPerson(personId, "", "", null, null);

        // Assert
        assertEquals("firstNameOne", testPerson.getFirstName(), "First name must remain the same because an empty string is not allowed as a first name.");
        assertEquals("lastNameOne", testPerson.getLastName(), "Last name must remain the same because an empty string is not allowed as a last name.");
    }

    @Test
    void testDeletePerson() {
        // Arrange
        Person testPerson = librarySystem.getPersons().get(0);

        // Act
        librarySystem.deletePerson(testPerson);

        // Assert
        assertFalse(librarySystem.getPersons().contains(testPerson), "The person has not been deleted.");
    }

    @Test
    void testDeletePersonWithFee() {
        // Arrange
        Person testPerson = librarySystem.getPersons().get(0);
        testPerson.addFee(5.0);

        // Act
        librarySystem.deletePerson(testPerson);

        // Assert
        assertTrue(librarySystem.getPersons().contains(testPerson), "Person should continue to exist and must not be deleted.");
    }

    @Test
    void testDeletePersonWithBorrowedBook() {
        // Arrange
        Person testPerson = librarySystem.getPersons().get(0);
        Book borrowedBook = librarySystem.getBooks().get(0);

        librarySystem.borrowBook(borrowedBook, testPerson);

        // Act
        librarySystem.deletePerson(testPerson);

        // Assert
        assertTrue(librarySystem.getPersons().contains(testPerson), "Person should continue to exist and must not be deleted.");
    }

    @Test
    void testDeletePersonFromEmptyList() {
        //Arrange
        librarySystem.getPersons().clear();
        Person person = new Person("firstNameOne", "lastNameOne", LocalDate.of(2001, 1, 1), null);
        int arrayListSizeBeforeDelete = librarySystem.getPersons().size();

        //Act
        librarySystem.deletePerson(person);

        //Assert
        assertEquals(0, arrayListSizeBeforeDelete, "ArrayList should be empty before removing.");
        assertEquals(0, librarySystem.getPersons().size(), "Amount of Array should not have changed since ArrayList was empty from the start.");
        boolean isRemovedPersonFound = librarySystem.getPersons().contains(person);
        assertFalse(isRemovedPersonFound, "Person should not be in the ArrayList.");
    }

    @Test
    void testDeletePersonWithNullPerson() {
        //Arrange
        librarySystem.getPersons().clear();
        int arrayListSizeBeforeDelete = librarySystem.getPersons().size();

        //Act
        librarySystem.deletePerson(null);

        //Assert
        assertEquals(0, arrayListSizeBeforeDelete, "ArrayList should be empty before removing.");
        assertEquals(0, librarySystem.getPersons().size(), "Amount of Array should not have changed since ArrayList was empty from the start.");
    }

    @Test
    void testBorrowBook() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);

        // Act
        boolean result = librarySystem.borrowBook(book, person);

        // Assert
        assertTrue(result, "Book should be borrowed.");
        assertTrue(book.isBorrowed(), "Book status should be true.");
    }

    @Test
    void testBorrowBookWithNonExistentPersonOrBook() {
        // Arrange
        Book nonExistentBook = new Book("Non-Existent Book", "Unknown Author", "Unknown Genre", false);
        Person nonExistentPerson = new Person("Ghost", "Reader", LocalDate.of(2000,2,2));

        // Act
        boolean resultBook = librarySystem.borrowBook(nonExistentBook, librarySystem.getPersons().get(0));
        boolean resultPerson = librarySystem.borrowBook(librarySystem.getBooks().get(0), nonExistentPerson);

        // Assert
        assertFalse(resultBook, "A non-existent book should not be borrowable.");
        assertFalse(resultPerson, "A non-existent person should not be able to borrow a book.");
    }

    @Test
    void testBorrowAlreadyBorrowedBook() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person firstPerson = librarySystem.getPersons().get(0);
        Person secondPerson = librarySystem.getPersons().get(1);

        librarySystem.borrowBook(book, firstPerson);

        // Act
        boolean result = librarySystem.borrowBook(book, secondPerson);

        // Assert
        assertFalse(result, "A book that is already borrowed should not be borrowable by another person.");
        assertEquals(firstPerson, book.getPersonBorrowed().orElse(null), "The book should still be assigned to the first borrower.");
    }

    @Test
    void testBorrowBookThrowsException() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);

        Person person = new Person("test", "test", LocalDate.of(2000,1,1)) {
            @Override
            public void addNewBorrowedBook(Book book) {
                throw new IllegalArgumentException("Cannot borrow this book");
            }
        };

        librarySystem.addPerson(person);

        // Act
        boolean result = librarySystem.borrowBook(book, person);

        // Assert
        assertFalse(result, "The method should return false when an exception occurs.");
        assertTrue(librarySystem.wasErrorLogged(), "The error flag should be set when an exception occurs.");
    }

    @Test
    void testGaveBookBack() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        librarySystem.borrowBook(book, person);

        // Act
        boolean result = librarySystem.gaveBookBack(book, person, true);

        // Assert
        assertTrue(result, "The book should be successfully returned.");
        assertFalse(book.isBorrowed(), "The book's borrowed status should be false.");
    }

    @Test
    void testGaveBookBackPersonNotBorrower(){
        //Arrange
        Book book = librarySystem.getBooks().get(0);
        Person personBorrower = librarySystem.getPersons().get(0);
        Person personNotBorrower= librarySystem.getPersons().get(1);
        //Act
        boolean resultBorrow = librarySystem.borrowBook(book, personBorrower);
        boolean resultGaveBookBack = librarySystem.gaveBookBack(book, personNotBorrower, false);
        //Assert
        assertTrue(resultBorrow, "The book should be successfully borrowed.");
        assertFalse(resultGaveBookBack, "The returning of the book should not work because the person is not the borrower.");
        assertTrue(librarySystem.getBorrowBookPersons().containsValue(personBorrower.getId()), "The borrower should be in the Map.");
        assertEquals(personBorrower.getId(), librarySystem.getBorrowBookPersons().get(book.getId()), "The book should be still borrowed by the borrower.");
    }

    @Test
    void testGaveBookBackWithDamage(){
        //Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        double personFeeBefore = person.getOpenFees();
        //Act
        boolean resultBorrow = librarySystem.borrowBook(book, person);
        boolean resultGaveBookBack = librarySystem.gaveBookBack(book, person, true);
        //Assert
        assertTrue(resultBorrow, "The book should be successfully borrowed.");
        assertTrue(resultGaveBookBack, "The book should be successfully returned.");
        assertTrue(book.isDamaged(), "The book should be damaged.");
        assertTrue(personFeeBefore < person.getOpenFees(), "The fee of the person should have increased.");
    }

    @Test
    void testGaveBookthrowsException() {
        //Arrange
        Book book = librarySystem.getBooks().get(0);

        Person person = new Person ("test", "test", LocalDate.of(2000,1,1)) {
            @Override
            public void removeBorrowedBook(Book book) {
                throw new IllegalArgumentException("Cannot remove this book");
            }
        };
        librarySystem.addPerson(person);

        //Act
        boolean resultBorrow = librarySystem.borrowBook(book, person);
        boolean resultReturn = librarySystem.gaveBookBack(book, person, false);

        //Assert
        assertTrue(resultBorrow, "The book should be successfully borrowed.");
        assertFalse(resultReturn, "The method should return false when an exception occurs.");
        assertTrue(librarySystem.wasErrorLogged(), "The error flag should be set when an exception occurs.");
    }

    @Test
    void testFilterBooksWithGenre() {
        // Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        Book book4 = new Book("Titel4", "Author4", "Testgenre1", false);
        librarySystem.addBook(book4);
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
        // Arrange
        Book book1 = librarySystem.getBooks().get(0);
        Book book2 = librarySystem.getBooks().get(1);
        Book book3 = librarySystem.getBooks().get(2);
        String author = book2.getAuthor();
        Book book4 = new Book("Title4", author, "Genre4", false);
        librarySystem.addBook(book4);
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
        bookFilter.setTitle("Test Book Title1");
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
        String author = book2.getAuthor();
        String genre = book2.getGenre();
        Book book4 = new Book("TestTitle4", author, genre, false);
        librarySystem.addBook(book4);
        //Act
        BookFilter bookFilter = new BookFilter();
        bookFilter.setTitle("Te");
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
        assertEquals(0, filteredBooks.size(), "No book should have been filtered because no book is in the list.");
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
        assertEquals(1, filteredPersons.size(), "Only one person should be returned.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 should not be included.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 should not be included.");
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
        assertEquals(0, filteredPersons.size(), "No person should be returned.");
        assertFalse(filteredPersons.contains(testPerson1), "Person 1 should not be included.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 should not be included.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 should not be included.");
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
        assertEquals(2, filteredPersons.size(), "All two persons should be returned.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 should be included.");
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
        assertEquals(2, filteredPersons.size(), "Only persons with fees >= 5.0 should be returned.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 should be included.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 should not be included.");
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
        assertEquals(2, filteredPersons.size(), "Only persons with fees <= 5.0 should be returned.");
        assertTrue(filteredPersons.contains(testPerson3), "Person 3 should be included.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 should not be included.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
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
        assertEquals(2, filteredPersons.size(), "Only two persons with minimum two books should be returned.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 should be included.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 should not be included.");
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
        assertEquals(2, filteredPersons.size(), "Only two persons with maximum two books should be returned.");
        assertFalse(filteredPersons.contains(testPerson1), "Person 1 should not be included.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 should not be included.");
        assertTrue(filteredPersons.contains(testPerson3), "Person 3 should be included.");
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
        assertEquals(1, filteredPersons.size(), "Only one person with these books and fees should be returned.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
        assertFalse(filteredPersons.contains(testPerson2), "Person 2 should not be included.");
        assertFalse(filteredPersons.contains(testPerson3), "Person 3 should not be included.");
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
        assertEquals(3, filteredPersons.size(), "All persons that are included in the LibrarySystem should be returned.");
        assertTrue(filteredPersons.contains(testPerson1), "Person 1 should be included.");
        assertTrue(filteredPersons.contains(testPerson2), "Person 2 should be included.");
    }

    @Test
    void testFilterPersonsWithEmptyData() {
        // Arrange
        librarySystem.getPersons().clear();

        // Act
        PersonFilter personFilter = new PersonFilter();

        List<Person> filteredPersons = librarySystem.filterPersons(personFilter);

        // Assert
        assertEquals(0, filteredPersons.size(), "No person should be returned.");
    }

    @Test
    void testCalculateFeeForBookLessThan30DaysLate() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        book.setBorrow(person, LocalDate.now().minusDays(20));

        // Act
        double fee = librarySystem.calculateFeeForBook(book, false);

        // Assert
        assertEquals(0.0, fee, "There should be no fee.");
    }

    @Test
    void testCalculateFeeForBookSameDay() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        book.setBorrow(person, LocalDate.now());

        // Act
        double fee = librarySystem.calculateFeeForBook(book, false);

        // Assert
        assertEquals(0.0, fee, "There should be no fee.");
    }

    @Test
    void testCalculateFeeForBookMoreThan30DaysLate() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        book.setBorrow(person, LocalDate.now().minusDays(40));

        // Act
        double fee = librarySystem.calculateFeeForBook(book, false);

        double expectedFee = Math.ceil(40 / 7.0) * Book.getFeeForOneWeek();

        // Assert
        assertEquals(expectedFee, fee, "The fee should be calculated correctly.");
    }

    @Test
    void testCalculateFeeForBookExact30DaysLate() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        book.setBorrow(person, LocalDate.now().minusDays(30));

        // Act
        double fee = librarySystem.calculateFeeForBook(book, false);

        // Assert
        assertEquals(0.0, fee, "The fee should be calculated correctly.");
    }

    @Test
    void testCalculateFeeForBookBookDamaged() {
        // Arrange
        Book book = librarySystem.getBooks().get(0);
        Person person = librarySystem.getPersons().get(0);
        book.setBorrow(person, LocalDate.now().minusDays(40));

        // Act
        double fee = librarySystem.calculateFeeForBook(book, true);

        double expectedFee = Book.getFeeForDamagedBook() + Math.ceil(40 / 7.0) * Book.getFeeForOneWeek();

        // Assert
        assertEquals(expectedFee, fee, "The fee should include the damage fee.");
    }

    @Test
    void testCalculateFeeForBookBookAlreadyDamaged() {
        // Arrange
        Book book = librarySystem.getBooks().get(2);
        Person person = librarySystem.getPersons().get(0);
        book.setBorrow(person, LocalDate.now().minusDays(40));

        // Act
        double fee = librarySystem.calculateFeeForBook(book, true);

        double expectedFee = Math.ceil(40 / 7.0) * Book.getFeeForOneWeek();

        // Assert
        assertEquals(expectedFee, fee, "The fee should not include a damage fee because it was already damaged.");
    }
}