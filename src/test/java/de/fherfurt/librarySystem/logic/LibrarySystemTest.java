package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Address;
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

        Address address1 = new Address("Musterstraße", 1, "99999", "Musterhausen", "Musterland");
        Address address2 = new Address("Mustergasse", 2, "11111", "Mustercity", "Musterland");

        // TODO: wenn jemand hier was ändert, muss das in den Tests auch geändert werden
        librarySystem.addPerson(new Person("VornameEins", "NachnameEins", LocalDate.of(2001, 1, 1), address1));
        librarySystem.addPerson(new Person("VornameZwei", "NachnameZwei", LocalDate.of(2002, 2, 2), address2));
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
        Person testPerson = new Person("Anna", "Beispiel", LocalDate.of(2000, 6, 10), new Address("Hauptstraße", 2,"11111", "Erfurt", "Deutschland"));
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
        Person testPerson = new Person("Alice", "Miller", LocalDate.of(1995, 5, 20), new Address("Hauptstraße", 2,"11111", "Erfurt", "Deutschland"));
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