package wia1002_assignment;

public class BorrowStack {
    private Node top;
    private int size;

    private static class Node {
        private final Book book;
        private final Node next;

        private Node(Book book, Node next) {
            this.book = book;
            this.next = next;
        }
    }

    /**
     * Pushes a borrowed book onto the top of the history stack.
     */
    public void push(Book book) {
        if (book == null) {
            System.out.println("Cannot add an empty book to borrowing history.");
            return;
        }

        top = new Node(book, top);
        size++;
    }

    /**
     * Returns true when there are no borrowed books in the history.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns the number of books currently stored in the history.
     */
    public int size() {
        return size;
    }

    /**
     * Displays the history in LIFO order (Most Recent First).
     */
    public void show() {
        if (isEmpty()) {
            System.out.println("Borrowing history is currently empty.");
            return;
        }

        System.out.println("\n--- Borrowing History (Most Recent First) ---");

        Node current = top;
        while (current != null) {
            Book b = current.book;
            System.out.println("[ISBN: " + b.isbn + "] " + b.title + " by " + b.authorName);
            current = current.next;
        }

        System.out.println("---------------------------------------------");
    }
}
