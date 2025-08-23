package com.LibraryApp.dto;

public class BorrowDto {
    private Long bookId;          // Ödünç alınacak kitabın ID'si
    private Long studentId;       // Ödünç alan öğrencinin ID'si
    private String bookTitle;     // Kitap başlığı
    private String bookAuthor;    // Kitap yazarı
    private String studentName;   // Öğrenci adı
    private String studentSurname; // Öğrenci soyadı

    // Default constructor
    public BorrowDto() {}

    // Parametreli constructor
    public BorrowDto(Long bookId, Long studentId, String bookTitle, String bookAuthor, String studentName, String studentSurname) {
        this.bookId = bookId;
        this.studentId = studentId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.studentName = studentName;
        this.studentSurname = studentSurname;
    }

    // Getter ve Setter metodları
    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getBookTitle() {return bookTitle;}
    public void setBookTitle(String bookTitle) {this.bookTitle = bookTitle;}
    public String getBookAuthor() {return bookAuthor;}
    public void setBookAuthor(String bookAuthor) {this.bookAuthor = bookAuthor;}
    public String getStudentName() {return studentName;}
    public void setStudentName(String studentName) {this.studentName = studentName;}
    public String getStudentSurname() {return studentSurname;}
    public void setStudentSurname(String studentSurname) {this.studentSurname = studentSurname;}

    @Override
    public String toString() { //toString ile bilgileri yazdırabiliyoruz.
        return "BorrowDto{" +
                "bookId=" + bookId +
                ", studentId=" + studentId +
                '}';
    }
}
