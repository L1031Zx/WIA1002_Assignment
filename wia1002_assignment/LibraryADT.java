package wia1002_assignment;

public interface LibraryADT {
    void addBook(int isbn,String title,String authorName);
    void searchBookByISBN(int isbn);
    void searchBookByTitle(String title);
    void searchBookByAuthorName(String authorName);
    void borrowBook(int isbn);
    void viewLatestHistory();
}
