package com.LibraryApp.dto;

// Kitap verilerini temsil eden DTO (Data Transfer Object) sınıfı. Kitap bilgilerini json formatında almak için.
public class BookDto {

    private Long id;           // Kitap ID
    private String title;      // Kitap başlık
    private String author;     // Yazar

    // Default constructor
    public BookDto() {}

    // Parametreli constructor
    public BookDto(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getter ve Setter metodları
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}

    @Override
    public String toString() { //toString ile bilgileri yazdırabiliyoruz.
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
