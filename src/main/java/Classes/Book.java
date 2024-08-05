package Classes;

public class Book {
    private String BookId;
    private String BookName;
    private String Author;
    private Boolean isAvailable;

    public Book(String bookId, String bookName, String author, Boolean isAvailable) {
        BookId = bookId;
        BookName = bookName;
        Author = author;
        this.isAvailable = isAvailable;
    }

    public String getBookId() {
        return BookId;
    }

    public void setBookId(String bookId) {
        BookId = bookId;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
