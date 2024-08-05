package App;

import Classes.Book;
import Classes.Member;
import Methods.BookActions;
import Methods.MemberActions;
import java.util.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Boolean start = true;
        String filePath = "books.json";
        String memberFilePath = "members.json";
        Scanner scanner = new Scanner(System.in);
        Member signedInMember = null;
        boolean loginSuccess = false;
        boolean isAdmin = false;


        while (start) {
            StartMenu();
            System.out.println("Select an Option\n");
            int userSelection = 0;
            try {
                userSelection = Integer.parseInt(scanner.nextLine());
            } catch (RuntimeException e) {
                System.out.println("Invalid Option");
            }
            switch (userSelection) {
                case 1:
                    signedInMember = MemberActions.LogIn(memberFilePath);
                    loginSuccess = true;
                    break;
                case 2:
                    signedInMember = MemberActions.SignUp(scanner, memberFilePath);
                    loginSuccess = true;
                    break;
                case 3:
                    start = false;
                    break;
                default:
                    loginSuccess = false;
                    break;
            }

            while (loginSuccess) {
                isAdmin = signedInMember.getName().equals("Admin");
                while (isAdmin && loginSuccess) { // Loop for admin panel
                    adminPanel();
                    int adminSelection = 0;
                    try {
                        adminSelection = Integer.parseInt(scanner.nextLine());
                    } catch (RuntimeException e) {
                        System.out.println("Please enter a valid number");
                    }

                    switch (adminSelection) {
                        case 1:
                            ArrayList<Member> members = MemberActions.loadExistingMembers(memberFilePath);
                            MemberActions.displayMembers(members);
                            break;
                        case 2:
                            ArrayList<Book> books = BookActions.loadExistingBooks(filePath);
                            BookActions.displayBooks(books);
                            break;
                        case 3:
                            ArrayList<Book> booksToAddTo = BookActions.loadExistingBooks(filePath);
                            Book newBook = BookActions.createNewBooks();
                            booksToAddTo.add(newBook);
                            BookActions.writeBooksToFile(booksToAddTo, filePath);
                            break;
                        case 4:
                            BookActions.removeBook(filePath);
                            break;
                        case 0:
                            isAdmin = false;
                            loginSuccess=false;// Exit admin panel loop
                            break;
                        default:
                            System.out.println("Invalid Option");
                            break;
                    }
                }

                // Non-admin actions
                if (!isAdmin && loginSuccess) {
                    displayMenu();
                    int mainMenuSelection = Integer.parseInt(scanner.nextLine());

                    switch (mainMenuSelection) {
                        case 1:
                            ArrayList<Book> currentBooks = BookActions.loadExistingBooks(filePath);
                            BookActions.displayBooks(currentBooks);
                            break;
                        case 2:
                            ArrayList<Book> borrowedBooks = (ArrayList<Book>) signedInMember.getBorrowedBooks();
                            if (borrowedBooks.size() == 0) {
                                System.out.println("No books borrowed for " + signedInMember.getName());
                            }
                            for (int i = 0; i < borrowedBooks.size(); i++) {
                                Book book = borrowedBooks.get(i);
                                System.out.println((i + 1) + ". " + book.getTitle() + " by " + book.getAuthor() + "\n");
                            }
                            break;
                        case 3:
                            Book returnedBook = BookActions.selectReturnedBook(signedInMember, scanner);
                            BookActions.returnBook(signedInMember, returnedBook, memberFilePath, filePath);
                            break;
                        case 4:
                            Book bookToBorrow = BookActions.getBook(filePath, scanner);
                            MemberActions.borrowBook(signedInMember, bookToBorrow,memberFilePath);
                            break;
                        case 0:
                            loginSuccess = false; // Logout
                            break;
                        default:
                            System.out.println("Invalid Option");
                            break;
                    }
                }
            }
        }
    }

    /*-----------------------------------------
        Main Menu Display
     ------------------------------------------*/

    public static void displayMenu() {
        System.out.println("1. View Available Books\n" +
                "2. View My Books\n" +
                "3. Return Book\n" +
                "4. Borrow Books\n" +
                "0. Log Out\n");
    }

    public static void adminPanel() {
        System.out.println("1. View All Members\n" +
                "2. View All Books\n" +
                "3. Add Books\n" +
                "4. Remove Books\n" +
                "0. Log Out\n");
    }

    public static void StartMenu() {
        System.out.println("1. Log in\n2. Sign up\n3. Exit");
    }
}