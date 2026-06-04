package wia1002_assignment;

import java.util.Stack;

public class BorrowStack {
    private Stack<Book> historyStack; // Holds the stack of borrowed books

    public BorrowStack() {
        this.historyStack = new Stack<>();
    }

    // Adds a book to the top of the stack
    public void push(Book borrowedBook) {
        historyStack.push(borrowedBook);
    }

    // Returns the total number of borrowed books
    public int getCount() {
        return historyStack.size();
    }

    // Checks if a book exists in the stack by its ISBN
    public boolean containsBook(int isbn) {
        for (Book currentBook : historyStack) {
            if (currentBook.getIsbn() == isbn) return true;
        }
        return false;
    }

    // Removes the most recent matching book copy from the stack
    public void removeBookFromHistory(int isbn) {
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            if (historyStack.get(i).getIsbn() == isbn) {
                historyStack.remove(i);
                break;
            }
        }
    }
    
    // Finds a book from top to bottom and returns its days borrowed
    public int getDaysBorrowedForBook(int isbn) {
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            Book targetBook = historyStack.get(i);
            if (targetBook.getIsbn() == isbn) {
                return targetBook.getDaysBorrowed();
            }
        }
        return 0; 
    }

    // Prints the borrowing history stack from newest to oldest
    public void printHistory() {
        if (historyStack.isEmpty()) {
            System.out.println("\n[!] You have no active borrowed books.");
            return;
        }
        System.out.println("\n==============================================================================================================================");
        System.out.println("                                         YOUR BORROWED BOOKS (Most Recent First)                                 ");
        System.out.println("==============================================================================================================================");
        int displayCount = 1;
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            System.out.println(" " + displayCount + ". " + historyStack.get(i).toHistoryString());
            displayCount++;
        }
        System.out.println("==============================================================================================================================");
    }
}