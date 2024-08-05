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

public class MemberActions {

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



    public static void displayMembers(ArrayList<Member> members) {
        StringBuilder output = new StringBuilder();
        for (Member member : members) {
            output.append("___________________________" +
                    "\nName: ").append(member.getName()).append("\n");
            output.append("Email: ").append(member.getEmail()).append("\n");
            output.append("\nBorrowed Books:\n");
            if (member.getBorrowedBooks().isEmpty()) {
                output.append("No books borrowed.\n");
            } else {
                for (Book book : member.getBorrowedBooks()) {
                    output.append("  Title: ").append(book.getTitle()).append("\n");
                    output.append("  Author: ").append(book.getAuthor()).append("\n" +
                            "___________________________");
                }
            }
            output.append("\n");
        }
        System.out.println(output.toString());
    }


    public static ArrayList<Member> loadExistingMembers(String memberFilePath){
        ArrayList<Member> existingMembers = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(memberFilePath)) {
            Reader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<ArrayList<Member>>() {
            }.getType();
            existingMembers = gson.fromJson(reader, listType); //Reading the existing members from the file.
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingMembers;
    }

    public static void writeMembers(ArrayList<Member> members, String filePath) {
        try (OutputStream outputStream = new FileOutputStream(filePath);
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            gson.toJson(members, writer); //writes the new members list to the current List of members
            System.out.println("Members updated and written to JSON file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeMemberToFile(Member member, String memberFilePath) {
        ArrayList<Member> members = loadExistingMembers(memberFilePath);
        for(int i = 0; i < members.size(); i++){
            if(members.get(i).getName().equals(member.getName())){
                members.set(i, member);
                break;
            }
        }
        writeMembers(members,memberFilePath);
    }

    public static void borrowBook(Member member, Book book, String memberFilePath){
        member.addBorrowedBook(book);

        writeMemberToFile(member, memberFilePath);
    }

    public static Member LogIn(String FilePath) {
        Member signedInMember = null;

        Scanner scanner = new Scanner(System.in);
        ArrayList<Member> currentMembers = loadExistingMembers(FilePath);

        boolean validUsername = false;
        while (!validUsername) {
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();
            for (Member member : currentMembers) {
                if (member.name.equals(username)) {
                    validUsername = true;
                    System.out.println("Enter Password: ");
                    String password = scanner.nextLine();
                    if (member.password.equals(password)) {
                        signedInMember = member;
                        break;
                    } else {
                        System.out.println("Incorrect password.");
                        validUsername = false;
                    }
                }
            }
            if (!validUsername) {
                System.out.println("Username not found. Please try again.");
            }
        }
        return signedInMember;
    }


    public static Member SignUp(Scanner scanner, String filePath) {
        ArrayList<Member> existingMembers = loadExistingMembers(filePath);

        System.out.println("Enter the Your name and surname: ");
        String addedName = scanner.nextLine();

        System.out.println("Enter the Password of the member: ");
        String addedPassword = scanner.nextLine();

        System.out.println("Enter your email : ");
        String addedEmail = scanner.nextLine();

        ArrayList<Book> borrowedBooks = new ArrayList<>();

        Member newMember = new Member(addedName, addedPassword, addedEmail, borrowedBooks);
        existingMembers.add(newMember);
        writeMembers(existingMembers, filePath);
        return newMember;
    }
}
