package Classes;

import java.util.ArrayList;

public class Library {
    private ArrayList<User> users;
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> unavailableBooks;

    public Library(ArrayList<User> users, ArrayList<Book> availableBooks, ArrayList<Book> unavailableBooks) {
        this.users = users;
        this.availableBooks = availableBooks;
        this.unavailableBooks = unavailableBooks;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Book> getAvailableBooks() {
        return availableBooks;
    }

    public ArrayList<Book> getUnavailableBooks() {
        return unavailableBooks;
    }

    public void addBookToAvailable(Book book) {
        availableBooks.add(book);
        book.setAvailable(true);
    }

    public void addBookToUnavailable(Book book) {
        unavailableBooks.add(book);
        book.setAvailable(false);
    }
}
