package wia1002_assignment;

public class BookBST {
    // Information Hiding: keep the root private
    private Book root;

    // Public method to insert a book into the BST
    public void insert(int isbn, String title, String authorName) {
        root = insertRecursive(root, isbn, title, authorName);
    }

    private Book insertRecursive(Book current, int isbn, String title, String authorName) {
        if (current == null) {
            return new Book(isbn, title, authorName);
        }

        if (isbn < current.isbn) {
            current.left = insertRecursive(current.left, isbn, title, authorName);
        } else if (isbn > current.isbn) {
            current.right = insertRecursive(current.right, isbn, title, authorName);
        }
        
        return current;
    }

    // =========================================================================
    // YOUR CORE TASK: RECORD FINDER (O(log n) Recursive Search)
    // =========================================================================
    public Book search(int isbn) {
        return sea(root, isbn);
    }

    // Recursive helper method matching your assigned task name 'sea'
    private Book sea(Book current, int isbn) {
        // Base Cases: root is null (not found) or key is present at root
        if (current == null || current.isbn == isbn) {
            return current;
        }

        // Val is smaller than root's key -> search left subtree
        if (isbn < current.isbn) {
            return sea(current.left, isbn);
        }

        // Val is greater than root's key -> search right subtree
        return sea(current.right, isbn);
    }
}