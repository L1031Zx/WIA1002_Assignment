package wia1002_assignment;

public class BookBST {
    private Book rootNode; // The top starting point of the tree
    private boolean matchFound; // Flag to track if search text matches any books

    public BookBST() {
        this.rootNode = null;
    }

    // Starts the book cataloging process
    public void insert(int isbn, String title, String authorName) {
        rootNode = insertRecursive(rootNode, isbn, title, authorName);
    }

    // Recursively finds the correct spot and adds the book
    private Book insertRecursive(Book currentNode, int isbn, String title, String authorName) {
        // If spot is empty, create the new book card
        if (currentNode == null) {
            return new Book(isbn, title, authorName);
        }

        // Navigate left for smaller ISBN, right for larger ISBN
        if (isbn < currentNode.getIsbn()) {
            currentNode.setLeft(insertRecursive(currentNode.getLeft(), isbn, title, authorName));
        } else if (isbn > currentNode.getIsbn()) {
            currentNode.setRight(insertRecursive(currentNode.getRight(), isbn, title, authorName));
        } else {
            // Same ISBN and same title: increase available stock
            if (currentNode.getTitle().equalsIgnoreCase(title)) {
                currentNode.setAvailableCopies(currentNode.getAvailableCopies() + 1);
            }
        }
        return currentNode;
    }

    // Public method to look up a book using its ISBN
    public Book searchByISBN(int isbn) {
        return searchIsbnRecursive(rootNode, isbn);
    }

    // Recursively searches the tree splits by comparing ISBN sizes
    private Book searchIsbnRecursive(Book currentNode, int isbn) {
        if (currentNode == null || currentNode.getIsbn() == isbn) {
            return currentNode;
        }
        if (isbn < currentNode.getIsbn()) {
            return searchIsbnRecursive(currentNode.getLeft(), isbn);
        }
        return searchIsbnRecursive(currentNode.getRight(), isbn);
    }

    // Lowers stock count by 1; erases node if stock hits 0
    public void removeCopy(int isbn) {
        Book targetBook = searchByISBN(isbn);
        if (targetBook != null) {
            targetBook.setAvailableCopies(targetBook.getAvailableCopies() - 1);
            if (targetBook.getAvailableCopies() <= 0) {
                rootNode = deleteNodeRecursive(rootNode, isbn);
            }
        }
    }

    // Completely deletes a book node regardless of stock levels
    public void deletePermanently(int isbn) {
        rootNode = deleteNodeRecursive(rootNode, isbn);
    }

    // Standard recursive tree deletion algorithm to rearrange branches
    private Book deleteNodeRecursive(Book currentNode, int isbn) {
        if (currentNode == null) return null;

        // Navigate down branches to find the book
        if (isbn < currentNode.getIsbn()) {
            currentNode.setLeft(deleteNodeRecursive(currentNode.getLeft(), isbn));
        } else if (isbn > currentNode.getIsbn()) {
            currentNode.setRight(deleteNodeRecursive(currentNode.getRight(), isbn));
        } else {
            // Target found! Handle branch rewiring:
            
            // Case 1 & 2: Node has 0 or 1 child branch
            if (currentNode.getLeft() == null) return currentNode.getRight();
            if (currentNode.getRight() == null) return currentNode.getLeft();

            // Case 3: Node has 2 child branches. 
            // Find the smallest node from right side (In-order Successor)
            Book successor = findMinValue(currentNode.getRight());
            
            // Delete the successor from its old deep location inside the right branch
            Book newRightSubtree = deleteNodeRecursive(currentNode.getRight(), successor.getIsbn());
            
            // Rewire successor pointers to take over the currentNode's structural position
            successor.setRight(newRightSubtree);
            successor.setLeft(currentNode.getLeft());
            
            // Return successor to update the parent's pointer references seamlessly
            return successor;
        }
        return currentNode;
    }

    // Finds the lowest numeric value in a given sub-tree layout
    private Book findMinValue(Book subTreeRoot) {
        Book lowestValuedNode = subTreeRoot;
        while (subTreeRoot.getLeft() != null) {
            lowestValuedNode = subTreeRoot.getLeft();
            subTreeRoot = subTreeRoot.getLeft();
        }
        return lowestValuedNode;
    }

    // Public method to print the entire catalog sorted by ISBN
    public void printInOrder() {
        if (rootNode == null) {
            System.out.println("[!] The library catalog is currently empty.");
            return;
        }
        printInOrderRecursive(rootNode);
    }

    // In-order traversal: Prints Left branch, then active Root, then Right branch
    private void printInOrderRecursive(Book currentNode) {
        if (currentNode != null) {
            printInOrderRecursive(currentNode.getLeft());
            System.out.println(" -> " + currentNode);
            printInOrderRecursive(currentNode.getRight());
        }
    }

    // Traverses the tree to find keyword matches anywhere in book titles
    public void searchByTitle(String title) {
        matchFound = false;
        searchTitleRecursive(rootNode, title.toLowerCase());
        if (!matchFound) System.out.println("[RESULT] No books found matching that title keyword.");
    }

    private void searchTitleRecursive(Book currentNode, String targetKeyword) {
        if (currentNode != null) {
            searchTitleRecursive(currentNode.getLeft(), targetKeyword);
            if (currentNode.getTitle().toLowerCase().contains(targetKeyword)) {
                System.out.println(" -> " + currentNode);
                matchFound = true;
            }
            searchTitleRecursive(currentNode.getRight(), targetKeyword);
        }
    }

    // Traverses the tree to find keyword matches anywhere in author names
    public void searchByAuthorName(String author) {
        matchFound = false;
        searchAuthorRecursive(rootNode, author.toLowerCase());
        if (!matchFound) System.out.println("[RESULT] No books found matching that author's name.");
    }

    private void searchAuthorRecursive(Book currentNode, String targetKeyword) {
        if (currentNode != null) {
            searchAuthorRecursive(currentNode.getLeft(), targetKeyword);
            if (currentNode.getAuthorName().toLowerCase().contains(targetKeyword)) {
                System.out.println(" -> " + currentNode);
                matchFound = true;
            }
            searchAuthorRecursive(currentNode.getRight(), targetKeyword);
        }
    }
}