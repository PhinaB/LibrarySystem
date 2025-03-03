package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Address;
import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.*;

public class LibrarySystem {
    private Map<Integer, Integer> borrowBookPersons;
    private List<Person> persons;
    private List<Book> books;

    public LibrarySystem () {
        this.borrowBookPersons = new HashMap<>();
        this.persons = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Book> getBooks() {
        return books;
    }

    public Book addBook(Book book) {
        // TODO Lucas
        return book;
    }

    public boolean editBook(int bookId, String title, String author, String genre){
        //TODO Lucas
        return true;
    }

    public void deleteBook(Book book) {
        //TODO Lucas
    }

    public Person addPerson(Person person) {
        if(person==null){
            System.out.println("Person darf nicht null sein.");
            return null;
        }
        if(persons.contains(person)){
            System.out.println("Person bereits vorhenden in der Liste.");
            return null;
        }
        persons.add(person);
        return person;
    }

    public void editPerson(int personId, String firstName, String lastName, LocalDate birthDate, Address address) {
        for (Person person : persons) {
            if(person.getId() == personId) {
                if(firstName != null) {
                    person.setFirstName(firstName);
                }
                if(lastName != null) {
                    person.setLastName(lastName);
                }
                if(birthDate != null) {
                    person.setBirthDate(birthDate);
                }
                if(address != null) {
                    person.setAddress(address);
                }
            }
        }
    }

    public void deletePerson (Person person) {
        if(person == null){
            System.out.println("Person darf nicht null sein.");
        }
        int personId = person.getId();
        if(borrowBookPersons.containsValue(personId)) {
            System.out.println("Person mit Id" + personId + "kann nicht gelöscht werden, da noch folgende Bücher ausgeliehen sind: " + person.getBorrowedBooks().toString());
            return;
        }
        if(person.getOpenFees() > 0){
            System.out.println("Person mit Id" + personId + "kann nicht gelöscht werden, da noch folgende Gebühren offen sind: " + person.getOpenFees());
            return;
        }
        persons.remove(person);
    }

    public boolean borrowBook(Book book, Person person) {
        if (borrowBookPersons.containsValue(book.getId())) {
            return false;
        }

        person.addNewBorrowedBook(book);
        book.setBorrow(person);
        borrowBookPersons.put(person.getId(), book.getId());
        return true;
    }

    public boolean gaveBookBack(Book book, Person person, boolean isNowDamaged) {
        if (borrowBookPersons.containsKey(person.getId()) && borrowBookPersons.get(person.getId()).equals(book.getId())) {
            person.removeBorrowedBook(book);
            book.removeBorrow();
            double newFee = this.calculateFeeForBook(book, isNowDamaged);
            person.addFee(newFee);
            return true;
        }
        return false;
    }

    public double calculateFeeForBook(Book book, boolean isNowDamaged) {
        double fee = 0.0;
        if (isNowDamaged && !book.isDamaged()) {
            book.setDamaged(true);
            fee += Book.getFeeForDamagedBook();
        }

        int daysLate = (int) ChronoUnit.DAYS.between(book.getBorrowDate().orElse(LocalDate.now()), LocalDate.now());
        if (daysLate <= 30) { // TODO: anderweitig einen Monat angeben?
            return fee;
        }

        double weeksLate = Math.ceil(daysLate / 7.0);
        fee += weeksLate * Book.getFeeForOneWeek();

        return fee;
    }

    public List<Person> filterPersons(PersonFilter personFilter) {
        if(!personFilter.hasFilter()){
            return persons;
        }
        else {
            return persons.stream()
                    .filter(person -> personFilter.getMinOpenFees() == 0.0 || person.getOpenFees() >= personFilter.getMinOpenFees())
                    .filter(person -> personFilter.getMaxOpenFees() == 0.0 || person.getOpenFees() <= personFilter.getMaxOpenFees())
                    .filter(person -> personFilter.getMinBooksBorrowed() == 0 || person.countBorrowedBooks() >= personFilter.getMinBooksBorrowed())
                    .filter(person -> personFilter.getMaxBooksBorrowed() == 0 || person.countBorrowedBooks() <= personFilter.getMaxBooksBorrowed())
                    .filter(person -> personFilter.getBooks() == null || person.getBorrowedBooks().map(books -> books.stream().anyMatch(book -> personFilter.getBooks().contains(book))).orElse(false))
                    .collect(Collectors.toList());
        }
    }

    public List<Book> filterBooks (BookFilter bookFilter){
        //TODO Lucas
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Buchverleih System {")
                .append("ausgeliehene Bücher pro Person =").append(borrowBookPersons)
                .append(", Personen =[");

        for (Person person : persons) {
            sb.append(person.toString()).append(", ");
        }
        // remove the last comma and space: // TODO: könnte man weg lassen
        if (!persons.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("], Bücher =[");
        for (Book book : books) {
            sb.append(book.toString()).append(", ");
        }
        // remove the last comma and space:
        if (!books.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]}");
        return sb.toString();
    }
}
