/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wia1002_assignment;

/**
 *
 * @author leowa
 */

public interface LibraryADT {
    void addBook(int isbn,String title,String authorName);
    void searchBookByISBN(int isbn);
    void searchBookByTitle(String title);
    void searchBookByAuthorName(String authorName);
    void borrowBook(int isbn);
    void viewLatestHistory();
}
