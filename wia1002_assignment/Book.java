package wia1002_assignment;

public class Book {
    private int isbn;
    private String title;
    private String authorName;
    private int availableCopies;
    private int daysBorrowed;
    private double fineAmount; // Enhanced inventory tracking

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

    public int getDaysBorrowed() { 
        return daysBorrowed; 
    }

    public double getFineAmount() { 
        return fineAmount; 
    }

    public void setAvailableCopies(int copies) { 
        this.availableCopies = copies; 
    }

    public void setDaysBorrowed(int days) { 
        this.daysBorrowed = days; 
    }

    public void setFineAmount(double fine) { 
        this.fineAmount = fine; 
    }

    public void setTitle(String title) { 
        this.title = title; 
    }

    public void setAuthorName(String authorName) { 
        this.authorName = authorName; 
    }

    @Override
    public String toString() {
        return String.format("ISBN: %-6d | Title: %-25s | Author: %-20s | Available Copies: %d", isbn, title, authorName, availableCopies);
    }

    public String toHistoryString() {
        String baseMessage = String.format("ISBN: %-6d | Title: %-25s | Author: %-20s | Days Borrowed: %d day(s)", 
                isbn, title, authorName, daysBorrowed);
        if (fineAmount > 0) {
            return baseMessage + String.format(" -> [OVERDUE - Fine: $%.2f]", fineAmount);
        } else {
            return baseMessage + " -> [Within Grace Period]";
        }
    }
}
