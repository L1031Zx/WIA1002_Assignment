package wia1002_assignment;

import java.util.Stack;

public class BorrowStack {
    private Stack<Book> historyStack;

    public BorrowStack() {
        this.historyStack = new Stack<>();
    }

    // Adds a newly borrowed book to the user's active checkout list
    public void push(Book book) {
        historyStack.push(book);
    }

    public int getCount() {
        return historyStack.size();
    }

    public boolean containsBook(int isbn) {
        for (Book b : historyStack) {
            if (b.getIsbn() == isbn) return true;
        }
        return false;
    }

    public void removeBookFromHistory(int isbn) {
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            if (historyStack.get(i).getIsbn() == isbn) {
                historyStack.remove(i);
                break;
            }
        }
    }

    // Displays the user's active borrowed books, showing the newest activity first
    public void printHistory() {
        if (historyStack.isEmpty()) {
            System.out.println("\n[!] You have no active borrowed books.");
            return;
        }
        System.out.println("\n=======================================================================");
        System.out.println("               YOUR BORROWED BOOKS (Most Recent First)                 ");
        System.out.println("=======================================================================");
        int count = 1;
        for (int i = historyStack.size() - 1; i >= 0; i--) {
            System.out.println(" " + count + ". " + historyStack.get(i));
            count++;
        }
        System.out.println("=======================================================================");
    }
}