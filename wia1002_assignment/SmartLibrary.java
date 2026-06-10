package wia1002_assignment;

import java.util.Random;
import java.util.Stack;

public class SmartLibrary implements LibraryADT {
    private BookBST catalogue; // The library master catalog tree
    private BorrowStack history; // The student borrowing history tracker
    private Stack<Book> unpaidFineRecords; // Holds the books that have fines the student hasn't paid yet
    private double fineBalance; // Total outstanding fines owed
    private Random randomGenerator; // For simulating random borrow days

    public SmartLibrary() {
        this.catalogue = new BookBST();
        this.history = new BorrowStack();
        this.unpaidFineRecords = new Stack<>(); 
        this.fineBalance = 0.0;
        this.randomGenerator = new Random();
    }

    @Override
    public void preloadBook(int isbn, String title, String authorName) {
        catalogue.insert(isbn, title, authorName);
    }

    @Override
    public void addBook(int isbn, String title, String authorName) {
        Book existingBook = catalogue.searchByISBN(isbn);
        
        // If it's not in the active catalog, check if a student currently has it borrowed
        if (existingBook == null && history.containsBook(isbn)) {
            // Find the borrowed book to verify details
            // We use a dummy search or retrieve check to make sure the title matches
            if (history.containsBook(isbn)) {
                System.out.println("\n[ERROR] Cannot catalog book! ISBN " + isbn + " is already registered to a book currently borrowed by a student.");
                return;
            }
        }

        if (existingBook == null) {
            catalogue.insert(isbn, title, authorName);
            System.out.println("\n[SUCCESS] Brand new book successfully logged into the library system.");
        } else {
            // If the ISBN exists in the tree, make sure it's the exact same book title
            if (existingBook.getTitle().equalsIgnoreCase(title)) {
                catalogue.insert(isbn, title, authorName);
                System.out.println("\n[SUCCESS] Existing title recognized! Available copy stock increased by 1.");
            } else {
                System.out.println("\n[WARNING] Cataloging Denied! This ISBN is already registered to a different book: \"" + existingBook.getTitle() + "\".");
            }
        }
    }

    @Override
    public void removeBook(int isbn) {
        Book targetBook = catalogue.searchByISBN(isbn);
        if (targetBook != null) {
            catalogue.deletePermanently(isbn); 
            System.out.println("\n[SUCCESS] The book \"" + targetBook.getTitle() + "\" has been permanently removed from the library catalog.");
        } else {
            System.out.println("\n[ERROR] Removal failed. No book with ISBN " + isbn + " exists in the system.");
        }
    }

    @Override
    public void updateBookStock(int isbn, int newCopyCount) {
        Book targetBook = catalogue.searchByISBN(isbn);
        if (targetBook != null) {
            targetBook.setAvailableCopies(newCopyCount);
            System.out.println("\n[SUCCESS] Stock updated! \"" + targetBook.getTitle() + "\" now has " + newCopyCount + " copy/copies available.");
        } else {
            System.out.println("\n[ERROR] Update failed. No book found with ISBN: " + isbn);
        }
    }

    @Override
    public void editBookDetails(int isbn, String newTitle, String newAuthor) {
        Book targetBook = catalogue.searchByISBN(isbn);
        if (targetBook != null) {
            System.out.println("\n[SUCCESS] Book details updated successfully!");
            System.out.println("Old Details -> " + targetBook);
            targetBook.setTitle(newTitle);
            targetBook.setAuthorName(newAuthor);
            System.out.println("New Details -> " + targetBook);
        } else {
            System.out.println("\n[ERROR] Editing failed. No book found with ISBN: " + isbn);
        }
    }

    @Override
    public void searchBookByISBN(int isbn) {
        Book targetBook = catalogue.searchByISBN(isbn);
        if (targetBook != null) {
            System.out.println("\n[MATCH FOUND]:\n" + targetBook);
        } else {
            System.out.println("\n[RESULT] No book found with ISBN: " + isbn);
        }
    }

    @Override
    public void searchBookByTitle(String title) {
        System.out.println("\n[SEARCH RESULTS FOR TITLE: \"" + title + "\"]:");
        catalogue.searchByTitle(title);
    }

    @Override
    public void searchBookByAuthorName(String authorName) {
        System.out.println("\n[SEARCH RESULTS FOR AUTHOR: \"" + authorName + "\"]:");
        catalogue.searchByAuthorName(authorName);
    }

    @Override
    public void borrowBook(int isbn) {
        if (history.getCount() >= 5) {
            System.out.println("\n[REJECTED] Borrow Limit Reached! You cannot borrow more than 5 books at a time.");
            return;
        }

        Book bookToBorrow = catalogue.searchByISBN(isbn);
        if (bookToBorrow != null && bookToBorrow.getAvailableCopies() > 0) {
            Book borrowedBook = new Book(bookToBorrow.getIsbn(), bookToBorrow.getTitle(), bookToBorrow.getAuthorName());
            
            // Simulates checkout duration from 0 to 9 days
            int borrowDuration = randomGenerator.nextInt(10); 
            borrowedBook.setDaysBorrowed(borrowDuration);
            
            // Appends fine immediately to card if overdue
            if (borrowDuration > 5) {
                double copyFine = (borrowDuration - 5) * 0.50;
                borrowedBook.setFineAmount(copyFine); 
            }
            
            history.push(borrowedBook);
            catalogue.removeCopy(isbn);
            System.out.println("\n[SUCCESS] \"" + borrowedBook.getTitle() + "\" has been checked out!");
        } else {
            System.out.println("\n[ERROR] Sorry, this book is currently out of stock or does not exist.");
        }
    }

    @Override
    public void returnBook(int isbn) {
        if (history.containsBook(isbn)) {
            int totalDaysHeld = history.getDaysBorrowedForBook(isbn); 
            
            String originalTitle = "Borrowed Asset";
            String originalAuthor = "Unknown Author";
            Book targetBook = catalogue.searchByISBN(isbn); 
            if (targetBook != null) {
                originalTitle = targetBook.getTitle();
                originalAuthor = targetBook.getAuthorName();
            }
            
            // If late, creates a record and saves it into the unpaid fine records stack
            if (totalDaysHeld > 5) {
                double addedFine = (totalDaysHeld - 5) * 0.50; 
                fineBalance += addedFine;
                
                Book chargedReceipt = new Book(isbn, originalTitle, originalAuthor);
                chargedReceipt.setDaysBorrowed(totalDaysHeld);
                chargedReceipt.setFineAmount(addedFine); 
                unpaidFineRecords.push(chargedReceipt); // Saved here until paid
                
                System.out.printf("\n[NOTICE] Processing Overdue Return: Held for %d days.\n", totalDaysHeld);
                System.out.printf("A late fee of $%.2f has been added to your account billing statement.\n", addedFine);
            } else {
                System.out.printf("\n[INFO] Book returned on time within grace period (Total days held: %d).\n", totalDaysHeld);
            }
            
            history.removeBookFromHistory(isbn);
            catalogue.insert(isbn, originalTitle, originalAuthor);
            System.out.println("[SUCCESS] Return process finalized. Inventory restocked.");
        } else {
            System.out.println("\n[REJECTED] Invalid operation. You do not have a borrowed book with this ISBN.");
        }
    }

    @Override
    public boolean checkAndPayFines(boolean payNow) {
        // Quietly processes payment if decision flag is set to true
        if (payNow) {
            System.out.println("\nProcessing payment transaction... Please wait...");
            fineBalance = 0.0;
            unpaidFineRecords.clear(); // Empties the list because everything is now paid
            System.out.println("[SUCCESS] Payment accepted! Your outstanding balance is now clear.");
            return false; 
        }

        // Prints fine breakdown if decision flag is set to false
        System.out.println("\n=======================================================================");
        System.out.println("                      ACCOUNT FINE BALANCE STATEMENT                   ");
        System.out.println("=======================================================================");
        
        if (unpaidFineRecords.isEmpty() && fineBalance == 0.0) {
            System.out.println(" Your account is in excellent standing. No items have accrued late fees.");
            System.out.println("=======================================================================");
            return false;
        }

        System.out.println("Itemized Breakdown of Overdue Charges:");
        int recordCount = 1;
        for (Book currentBook : unpaidFineRecords) {
            System.out.printf("  %d. \"%s\" (ISBN: %d) held for %d days -> Charged: $%.2f\n", 
                    recordCount, currentBook.getTitle(), currentBook.getIsbn(), currentBook.getDaysBorrowed(), currentBook.getFineAmount());
            recordCount++;
        }
        
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf(" TOTAL OUTSTANDING BALANCE DUE: $%.2f\n", fineBalance);
        System.out.println("=======================================================================");
        System.out.println("[INFO] Payment deferred. This balance remains outstanding on your profile.");
        return true;
    }

    @Override
    public void displayEntireCatalogue() {
        System.out.println("\n=======================================================================");
        System.out.println("                  COMPLETE LIBRARY CATALOG (Sorted by ISBN)            ");
        System.out.println("=======================================================================");
        catalogue.printInOrder();
        System.out.println("=======================================================================");
    }

    @Override
    public void viewLatestHistory() {
        history.printHistory();
    }
}