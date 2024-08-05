package Methods;

import Classes.Book;
import Classes.Member;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class BookActions {



    //This excludes the Tread Class to avoid errors
    //when serializing and deserializing the JSON values
    static Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT)
            .excludeFieldsWithModifiers(Modifier.STATIC)
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return false;
                }
                @Override
                public boolean shouldSkipClass(Class<?> c) {
                    return c == Thread.class;
                }
            })
            .create();


    public static void displayBooks(ArrayList<Book> books) {
        System.out.println("Existing Books:");
        for (Book book : books) {
            System.out.println("Title : " + book.getTitle() + "\n" + "Author : " + book.getAuthor() + "\n" + "ISBN : " + book.getIsbn() + "\n");
        }
    }


    public static Book createNewBooks() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the book: ");
        String addedTitle = scanner.nextLine();
        System.out.println("Enter the author of the book: ");
        String addedAuthor = scanner.nextLine();
        System.out.println("Enter the ISBN Number of the book: ");
        String addedISBN = scanner.nextLine();

        Book newBook = new Book(addedTitle, addedAuthor, addedISBN, true );
        return newBook;
    }

    public static void writeBooksToFile(ArrayList<Book> books, String filePath) {
        try (OutputStream outputStream = new FileOutputStream(filePath); // Write mode (overwrite)
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            gson.toJson(books, writer);
            System.out.println("Books updated and written to JSON file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Book getBook(String filepath ,Scanner scanner) {
        System.out.println("\nAvailable Books :");

        ArrayList<Book> books = BookActions.loadExistingBooks(filepath);
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor()); //iterate through books and return Info
        }

        int choice = 0;
        do {
            System.out.print("\nEnter the number of the book : ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > books.size()) {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + books.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while (choice < 1 || choice > books.size());

        return books.get(choice - 1); // return the book th the correct index
    }


    public static void returnBook(Member member, Book returnedBook, String memberFilePath, String bookFilePath) {
        member.getBorrowedBooks().remove(returnedBook);
        ArrayList<Book> existingBooks = BookActions.loadExistingBooks(bookFilePath);

        ArrayList<Member> existingMembers = MemberActions.loadExistingMembers(memberFilePath);
        //Arrow function to remove a Member with matching values
        existingMembers.removeIf(m -> m.getName().equals(member.getName()) && m.getEmail().equals(member.getEmail()));
        existingMembers.add(member); // Add the updated member object
        MemberActions.writeMembers(existingMembers, memberFilePath);
        existingBooks.add(returnedBook);
        BookActions.writeBooksToFile(existingBooks, bookFilePath);
    }

    public static void removeBook( String bookFilePath) {
        ArrayList<Book> existingBooks = BookActions.loadExistingBooks(bookFilePath);
        String bookName = BookActions.getBookNameFromUser(); // remove a Book by Name
        existingBooks.removeIf(b -> b.getTitle().equals(bookName));
        BookActions.writeBooksToFile(existingBooks, bookFilePath);//Overwrite the current list of Books with the updated
    }


    public static ArrayList<Book> loadExistingBooks(String filePath) {
        ArrayList<Book> existingBooks = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            Reader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<ArrayList<Book>>() {
            }.getType();
            existingBooks = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingBooks;
    }


    public static Book selectReturnedBook(Member member, Scanner scanner) {
        System.out.println("\nSelect a book to return:");

        ArrayList<Book> borrowedBooks = (ArrayList<Book>) member.getBorrowedBooks();
        for (int i = 0; i < borrowedBooks.size(); i++) {
            Book book = borrowedBooks.get(i);
            System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor());
        }

        int choice;
        do {
            System.out.print("\nEnter the number of the book to return: ");
            choice = scanner.nextInt();
        } while (choice < 1 || choice > borrowedBooks.size());

        // Return the selected book
        return borrowedBooks.get(choice - 1);
    }


    public static String getBookNameFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the book:");
        return scanner.nextLine();
    }
}
