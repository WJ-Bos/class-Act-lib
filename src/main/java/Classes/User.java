package Classes;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private UUID userId;
    private String userName;
    private String password;
    private ArrayList<Book> borrowedBooks;


    public User(UUID userId, String userName, String password, ArrayList<Book> borrowedBooks) {
        this.userId = UUID.randomUUID();
        this.userName = userName;
        this.password = password;
        this.borrowedBooks = borrowedBooks;
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addToBorrowedBooks(Book book) {
        borrowedBooks.add(book);
        book.setAvailable(false);
    }
}
