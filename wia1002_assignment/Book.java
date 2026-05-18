package wia1002_assignment;

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
