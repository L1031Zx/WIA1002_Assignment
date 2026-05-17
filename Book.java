/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wia1002_assignment;

/**
 *
 * @author leowa
 */
public class Book {
    int isbn;
    String title,authorName;
    Book left,right;
    
    public Book(int isbn,String title,String authorName){
        this.isbn=isbn;
        this.title=title;
        this.authorName=authorName;
        this.left=null;
        this.right=null;
    }
}
