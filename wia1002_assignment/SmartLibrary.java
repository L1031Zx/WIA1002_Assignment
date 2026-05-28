package wia1002_assignment;

import java.util.Scanner;
import java.util.InputMismatchException;

public class SmartLibrary implements LibraryADT {
    // private field added
    private final BookBST catalogue = new BookBST();
    private final BorrowStack history = new BorrowStack();

    @Override
    public void addBook(int isbn, String title, String authorName) {
        catalogue.insert(isbn, title, authorName); // Passes data directly to the BST insertion logic
        System.out.println("Success: Added \"" + title + "\".");
    }

    @Override
    public void searchBookByISBN(int isbn) {
        Book b = catalogue.search(isbn); // run recursive search function
        System.out.println(b != null
                ? "Found: [ISBN: " + b.isbn + "] " + b.title + " by " + b.authorName
                : "Not Found: No book matches ISBN " + isbn + ".");
    }

    @Override
    public void borrowBook(int isbn) {
        Book b = catalogue.search(isbn); // check if book exist before trying to borrow it
        if (b != null) {
            history.push(b); // pushes the existing book on the history stack
            System.out.println("Success: Borrowed \"" + b.title + "\".");
        } else {
            System.out.println("Error: ISBN " + isbn + " not found in catalogue.");
        }
    }

    @Override
    public void viewLatestHistory() {
        history.show(); // calls the stack display method to show records in stack order
    }

    @Override public void searchBookByTitle(String title) {
        System.out.println("Unsupported feature.");
    }

    @Override public void searchBookByAuthorName(String authorName) {
        System.out.println("Unsupported feature.");
    }

    //console ui
    public void runMenu() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                printMenu();
                int choice = readInt(sc, "Choice: ");

                if (choice == 5) {
                    System.out.println("Exiting system. Goodbye!");
                    break; // loop breaks
                }
                handleChoice(choice, sc);
            }
        }
    }

    private void printMenu() {
        System.out.print("\n--- SmartLibrary Menu ---\n1. Add Book\n2. Search (BST)\n3. Borrow (Stack)\n4. History\n5. Exit\n");
    }

    private void handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1 -> {
                int isbn = readInt(sc, "Enter ISBN: ");
                if (isbn == -1) return;

                System.out.print("Enter Title: ");   String title = sc.nextLine();  // sc.nextLine() handle space in title
                System.out.print("Enter Author: ");  String author = sc.nextLine(); // prevents scanner skipping
                addBook(isbn, title, author);
            }
            case 2 -> {
                int isbn = readInt(sc, "Enter ISBN to search: ");
                if (isbn != -1) searchBookByISBN(isbn); // only runs search if input is valid
            }
            case 3 -> {
                int isbn = readInt(sc, "Enter ISBN to borrow: ");
                if (isbn != -1) borrowBook(isbn); // only runs borrowing transaction if enter input is valid
            }
            case 4 -> viewLatestHistory(); // displays all borrowed books
            default -> System.out.println("Invalid choice (1-5 only).");
        }
    }

    private int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        try {
            int value = sc.nextInt();
            sc.nextLine(); // clears the hidden following newline character left behind in the buffer
            return value;
        } catch (InputMismatchException e) {
            System.out.println("Input Error: Please enter a valid integer numeric value.");
            sc.nextLine(); // flushes entered symbol and number
            return -1;     // ensures smart library end the process
        }
    }
}
