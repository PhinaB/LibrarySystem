package de.fherfurt.librarySystem.logic;

import de.fherfurt.librarySystem.models.Book;
import de.fherfurt.librarySystem.models.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

public class LibrarySystem {
    private Map<Integer, Integer> borrowBookPersons;
    private List<Person> persons;
    private List<Book> books;

    public LibrarySystem () {}

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LibrarySystem{")
                .append("borrowBookPersons=").append(borrowBookPersons)
                .append(", persons=[");

        for (Person person : persons) {
            sb.append(person.toString()).append(", ");
        }
        // remove the last comma and space: // TODO: könnte man weg lassen
        if (!persons.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("], books=[");
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
