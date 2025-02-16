package de.fherfurt.librarySystem.models;

public class Book implements Comparable<Book> {

private String title;
private String author;
private String genre;
private boolean isDamaged;

public Book(String title, String author, String genre) {
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.isDamaged = false;
}

public String getTitle() {
    return title;
}

public String getAuthor() {
    return author;
}

public String getGenre() {
    return genre;
}

public boolean isDamaged() {
    return isDamaged;
}

public void setDamaged(boolean damaged) {
    isDamaged = damaged;
}

@Override
    public int compareTO( Book other) {
    return this.title.compareToIgnoreCase(other.title);
}

@Override
    public String toString() {
    return "Book{title=" + title + "author= " + author + "genre= " + genre + "isDamaged= " + isDamaged + "}";
}
}
