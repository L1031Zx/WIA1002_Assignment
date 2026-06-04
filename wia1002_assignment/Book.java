package wia1002_assignment;

public class Book {
    private int isbn;
    private String title;
    private String authorName;
    private int availableCopies; // Enhanced inventory tracking

    // Binary Search Tree children pointers
    public Book left;
    public Book right;

    public Book(int isbn, String title, String authorName) {
        this.isbn = isbn;
        this.title = title;
        this.authorName = authorName;
        this.availableCopies = 1; // Starts with 1 copy by default
        this.left = null;
        this.right = null;
    }

    // Getters and Setters
    public int getIsbn() { 
        return isbn; 
    }

    public String getTitle() { 
        return title; 
    }

    public String getAuthorName() { 
        return authorName; 
    }

    public int getAvailableCopies() { 
        return availableCopies; 
    }
    
    public void setAvailableCopies(int copies) { 
        this.availableCopies = copies; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public void setAuthorName(String authorName) { 
        this.authorName = authorName; 
    }

    @Override
    public String toString() {
        return String.format("ISBN: %-6d | Title: %-25s | Author: %-20s | Available Copies: %d", 
                isbn, title, authorName, availableCopies);
    }
}
