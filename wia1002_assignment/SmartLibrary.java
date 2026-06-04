package wia1002_assignment;

import java.util.Random;

public class SmartLibrary implements LibraryADT {
    private BookBST catalogue;
    private BorrowStack history;
    private double fineBalance;
    private Random randomGenerator;

    public SmartLibrary() {
        this.catalogue = new BookBST();
        this.history = new BorrowStack();
        this.fineBalance = 0.0;
        this.randomGenerator = new Random();
    }

    @Override
    public void addBook(int isbn, String title, String authorName) {
        // Check if the book is already in our catalog database first
        Book existingBook = catalogue.searchByISBN(isbn);
        
        if (existingBook == null) {
            // Brand new book entry
            catalogue.insert(isbn, title, authorName);
            System.out.println("\n[SUCCESS] Brand new book successfully logged into the library system.");
        } else {
            // Same ISBN encountered. Verify if titles match to adjust stock
            if (existingBook.getTitle().equalsIgnoreCase(title)) {
                catalogue.insert(isbn, title, authorName);
                System.out.println("\n[SUCCESS] Existing title recognized! Available copy stock increased by 1.");
            } else {
                // Different title conflict
                System.out.println("\n[WARNING] Cataloging Denied! This ISBN is already registered to a different book: \"" + existingBook.getTitle() + "\".");
            }
        }
    }

    @Override
    public void removeBook(int isbn) {
        Book bookCheck = catalogue.searchByISBN(isbn);
        if (bookCheck != null) {
            catalogue.removeCopy(isbn); 
            System.out.println("\n[SUCCESS] The book \"" + bookCheck.getTitle() + "\" has been removed from the library catalog.");
        } else {
            System.out.println("\n[ERROR] Removal failed. No book with ISBN " + isbn + " exists in the system.");
        }
    }

    @Override
    public void updateBookStock(int isbn, int newCopyCount) {
        // Locate the book first using our fast search
        Book book = catalogue.searchByISBN(isbn);
        
        if (book != null) {
            // Change the stock number directly
            book.setAvailableCopies(newCopyCount);
            System.out.println("\n[SUCCESS] Stock updated! \"" + book.getTitle() + "\" now has " + newCopyCount + " copy/copies available.");
        } else {
            System.out.println("\n[ERROR] Update failed. No book found with ISBN: " + isbn);
        }
    }

    @Override
    public void editBookDetails(int isbn, String newTitle, String newAuthor) {
        // Locate the book using its unchangeable ISBN
        Book book = catalogue.searchByISBN(isbn);
        
        if (book != null) {
            // Create a custom method or handle updating the fields inside SmartLibrary securely
            // Because Book fields are private, we can temporarily recreate a clean modification loop,
            // or update Book.java to include setters for Title and Author if preferred!
            // To keep it simple, we can safely re-register the details or add standard setters to Book.java.
            System.out.println("\n[SUCCESS] Book details updated successfully!");
            System.out.println("Old Details -> " + book);
            
            // Let's add simple setter triggers (Make sure to look at the Book.java change below!)
            book.setTitle(newTitle);
            book.setAuthorName(newAuthor);
            
            System.out.println("New Details -> " + book);
        } else {
            System.out.println("\n[ERROR] Editing failed. No book found with ISBN: " + isbn);
        }
    }

    @Override
    public void searchBookByISBN(int isbn) {
        Book match = catalogue.searchByISBN(isbn);
        if (match != null) {
            System.out.println("\n[MATCH FOUND]:\n" + match);
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
        if (history.getCount() >= 3) {
            System.out.println("\n[REJECTED] Borrow Limit Reached! You cannot borrow more than 3 books at a time.");
            return;
        }

        Book bookToBorrow = catalogue.searchByISBN(isbn);
        if (bookToBorrow != null && bookToBorrow.getAvailableCopies() > 0) {
            Book historicalSnapshot = new Book(bookToBorrow.getIsbn(), bookToBorrow.getTitle(), bookToBorrow.getAuthorName());
            history.push(historicalSnapshot);
            catalogue.removeCopy(isbn);
            System.out.println("\n[SUCCESS] \"" + historicalSnapshot.getTitle() + "\" has been checked out!");
        } else {
            System.out.println("\n[ERROR] Sorry, this book is currently out of stock or does not exist.");
        }
    }

    @Override
    public void returnBook(int isbn) {
        if (history.containsBook(isbn)) {
            // Find what the book details were from our history stack before removing it
            String originalTitle = "Borrowed Asset";
            String originalAuthor = "Unknown Author";
            
            for (int i = 0; i < history.getCount(); i++) {
                // Find matching item record to preserve original naming structures
                Book activeCheck = catalogue.searchByISBN(isbn); 
                if (activeCheck != null) {
                    originalTitle = activeCheck.getTitle();
                    originalAuthor = activeCheck.getAuthorName();
                }
            }
            
            // Pull the book card out of the user's possession
            history.removeBookFromHistory(isbn);
            
            // Late fine handling evaluation routines
            int daysLate = randomGenerator.nextInt(10); 
            if (daysLate > 5) {
                double addedFine = (daysLate - 5) * 0.50; 
                fineBalance += addedFine;
                System.out.printf("\n[NOTICE] This book was returned late! A late fee of $%.2f has been added to your account.\n", addedFine);
            }
            
            // Re-insert safely with clean logging strings tracked explicitly here
            catalogue.insert(isbn, originalTitle, originalAuthor);
            System.out.println("\n[SUCCESS] Book returned successfully and added back to library inventory.");
        } else {
            System.out.println("\n[REJECTED] Invalid operation. You do not have a borrowed book with this ISBN.");
        }
    }

    @Override
    public void checkAndPayFines() {
        System.out.println("\n--- MY ACCOUNT BALANCE ---");
        System.out.printf("Current Late Fees Due: $%.2f\n", fineBalance);
        if (fineBalance > 0) {
            System.out.println("Processing your payment fee...");
            fineBalance = 0.0;
            System.out.println("[SUCCESS] Outstanding fees paid! Your account balance is clear.");
        } else {
            System.out.println("Your account is in good standing. No outstanding fees.");
        }
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