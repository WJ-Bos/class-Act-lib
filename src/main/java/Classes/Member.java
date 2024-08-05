package Classes;

import java.util.List;

public class Member {
    public String name;
    public String password;
    public String email;
    public List<Book> borrowedBooks;

    public Member(String name,String password, String email, List<Book> borrowedBooks) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }
}
