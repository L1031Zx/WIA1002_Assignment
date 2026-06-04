package wia1002_assignment;

public class BookBST {
    private Book root;
    private boolean matchFound;

    public BookBST() {
        this.root = null;
    }

    // Public method to start adding a book
    public void insert(int isbn, String title, String authorName) {
        root = insertRecursive(root, isbn, title, authorName);
    }

    private Book insertRecursive(Book current, int isbn, String title, String authorName) {
        // 1. If the spot is empty, create the new book record
        if (current == null) {
            return new Book(isbn, title, authorName);
        }

        if (isbn < current.getIsbn()) {
            current.left = insertRecursive(current.left, isbn, title, authorName);
        } else if (isbn > current.getIsbn()) {
            current.right = insertRecursive(current.right, isbn, title, authorName);
        } else {
            // If the title is the same, just increase the copy stock count
            if (current.getTitle().equalsIgnoreCase(title)) {
                current.setAvailableCopies(current.getAvailableCopies() + 1);
            }
            // If the title is completely different, we do nothing to protect the data
        }
        return current;
    }

    // Searches the library shelf using a specific ISBN number
    public Book searchByISBN(int isbn) {
        return searchIsbnRecursive(root, isbn);
    }

    private Book searchIsbnRecursive(Book current, int isbn) {
        if (current == null || current.getIsbn() == isbn) {
            return current;
        }
        if (isbn < current.getIsbn()) {
            return searchIsbnRecursive(current.left, isbn);
        }
        return searchIsbnRecursive(current.right, isbn);
    }

    // Lowers the copy count when a book is borrowed. Removes it from sight if copies hit 0
    public void removeCopy(int isbn) {
        Book b = searchByISBN(isbn);
        if (b != null) {
            b.setAvailableCopies(b.getAvailableCopies() - 1);
            if (b.getAvailableCopies() <= 0) {
                root = deleteNodeRecursive(root, isbn);
            }
        }
    }

    private Book deleteNodeRecursive(Book current, int isbn) {
        if (current == null) return null;

        if (isbn < current.getIsbn()) {
            current.left = deleteNodeRecursive(current.left, isbn);
        } else if (isbn > current.getIsbn()) {
            current.right = deleteNodeRecursive(current.right, isbn);
        } else {
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            current = findMinValue(current.right);
            current.right = deleteNodeRecursive(current.right, current.getIsbn());
        }
        return current;
    }

    private Book findMinValue(Book root) {
        Book minVal = root;
        while (root.left != null) {
            minVal = root.left;
            root = root.left;
        }
        return minVal;
    }

    public void printInOrder() {
        if (root == null) {
            System.out.println("[!] The library catalog is currently empty.");
            return;
        }
        printInOrderRecursive(root);
    }

    private void printInOrderRecursive(Book current) {
        if (current != null) {
            printInOrderRecursive(current.left);
            System.out.println(" -> " + current);
            printInOrderRecursive(current.right);
        }
    }

    public void searchByTitle(String title) {
        matchFound = false;
        searchTitleRecursive(root, title.toLowerCase());
        if (!matchFound) System.out.println("[RESULT] No books found matching that title keyword.");
    }

    private void searchTitleRecursive(Book current, String target) {
        if (current != null) {
            searchTitleRecursive(current.left, target);
            if (current.getTitle().toLowerCase().contains(target)) {
                System.out.println(" -> " + current);
                matchFound = true;
            }
            searchTitleRecursive(current.right, target);
        }
    }

    public void searchByAuthorName(String author) {
        matchFound = false;
        searchAuthorRecursive(root, author.toLowerCase());
        if (!matchFound) System.out.println("[RESULT] No books found matching that author's name.");
    }

    private void searchAuthorRecursive(Book current, String target) {
        if (current != null) {
            searchAuthorRecursive(current.left, target);
            if (current.getAuthorName().toLowerCase().contains(target)) {
                System.out.println(" -> " + current);
                matchFound = true;
            }
            searchAuthorRecursive(current.right, target);
        }
    }
}