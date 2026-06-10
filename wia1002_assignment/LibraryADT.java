package wia1002_assignment;

public interface LibraryADT {
    void addBook(int isbn,String title,String authorName);
    void removeBook(int isbn);
    void searchBookByISBN(int isbn);
    void searchBookByTitle(String title);
    void searchBookByAuthorName(String authorName);
    void borrowBook(int isbn);
    void viewLatestHistory();
    void returnBook(int isbn);
    void displayEntireCatalogue();
    void updateBookStock(int isbn, int newCopyCount);
    void editBookDetails(int isbn, String newTitle, String newAuthor);
    void preloadBook(int isbn, String title, String authorName);
    boolean checkAndPayFines(boolean payNow);
}
