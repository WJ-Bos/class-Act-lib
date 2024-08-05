package Classes;

public class Book {
    private String title;
    private String isbn;
    private String author;
    private Boolean isAvailable;

    public Book(String title, String isbn, String author, Boolean isAvailable) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }
}

