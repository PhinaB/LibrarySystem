package de.fherfurt.librarySystem.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Person {
    private static int idCounter=0;
    private final int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Address address;
    private ArrayList<Book> borrowedBooks;
    private double openFees;

    public Person(String firstName, String lastName, LocalDate birthDate, Address address) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.borrowedBooks = new ArrayList<>();
        this.openFees = 0.0;
    }

    public Person(String firstName, String lastName, LocalDate birthDate){
        this(firstName, lastName, birthDate, null);
    }

    public int getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public double getOpenFees() {
        return openFees;
    }

    public void setOpenFees(double openFees) {
        this.openFees = openFees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        // id- Prüfung reicht eigentlich
        return id == person.id && Double.compare(openFees, person.openFees) == 0 && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(birthDate, person.birthDate) && Objects.equals(address, person.address) && Objects.equals(borrowedBooks, person.borrowedBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, address, borrowedBooks, openFees);
    }

    @Override
    public String toString() {
        return String.format("%s, %s (%s), %s, %s, %.2f", firstName, lastName, birthDate, address, borrowedBooks, openFees);
    }
}
