package com.LibraryApp.controller;

import com.LibraryApp.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books") //Bu controller'ın tüm endpoint'leri /api/books ile başlar.
public class BookController {

    private final List<BookDto> books = new ArrayList<>(); //Dummy kitap listesi tüm metodlarda kullanılacak.
    private Long nextId = 6L; // Sonraki ID için sayaç ekliyoruz.

    public BookController() {     //Constructor'da örnek veriler ekliyoruz.
        books.add(new BookDto(1L, "Algoritma ve Programlama Mantığı", "Burak Tungut"));
        books.add(new BookDto(2L, "Projeler ile Python", "Mustafa Aydemir"));
        books.add(new BookDto(3L, "C++ ile Projeler", "Tolga Büyüktanır"));
        books.add(new BookDto(4L, "Javascript Programlama", "İbrahim Çelikbilek"));
        books.add(new BookDto(5L, "Hacking Interface", "Hamza Elbahadır"));
    }

    /*
     Kitapları listeleme endpointi:

     - HTTP Method: GET
     - URL: /api/books

     Örnekler:
     - /api/books → Tüm kitapları getirir.
     - /api/books?title=algoritma → Başlığında "algoritma" geçen kitapları getirir.
     - /api/books?author=tolga → Yazarı "tolga" olan kitapları getirir.
     - /api/books?title=java&author=ibrahim → Hem başlığında "java" hem yazarı "ibrahim" olan kitapları getirir.

     - required=false: Bu parametre zorunlu değil, gönderilmezse null olur
     - defaultValue="": Varsayılan değer boş string
     */
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") String author) {

        System.out.println("Kitaplar listeleniyor...");
        System.out.println("Filtreler - Başlık: '" + title + "', Yazar: '" + author + "'");
        List<BookDto> filteredBooks = books.stream() //Filtreleme işlemi
                .filter(book -> title.isEmpty() || book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> author.isEmpty() || book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Filtrelenmiş kitap sayısı: " + filteredBooks.size());

        return ResponseEntity.ok(filteredBooks);
    }

    @GetMapping("/{id}") //Tek kitap getir endpoint'i HTTP Method: GET URL: /api/books/{id}
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {

        System.out.println("Kitap aranıyor, ID: " + id);

        BookDto book = books.stream() //Listeden ID'ye göre kitap arar.
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    /*
     Yeni kitap ekle endpoint'i:

     - HTTP Method: POST
     - URL: /api/books
     - Content-Type: application/json

     Örnek:
     {
        "title": "Yeni Kitap",
        "author": "Yazar Adı",
     }
     */
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {

        System.out.println("Yeni kitap ekleniyor: " + bookDto);

        Long newId = (long) (books.size() + 1); //Yeni ID oluşturur ve kitabı listeye ekler.
        bookDto.setId(newId);
        books.add(bookDto);

        return ResponseEntity.ok("Kitap başarıyla eklendi! ID: " + newId);
    }

    /*
     Kitap güncelle endpoint'i:

     - HTTP Method: PATCH
     - URL: /api/books/{id}
     - Content-Type: application/json

     - PATCH metodunu sadece belirli alanları değiştirmek için kullanıyoruz.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {

        System.out.println("Kitap güncelleniyor, ID: " + id);
        System.out.println("Güncellenecek bilgiler: " + bookDto);

        for (int i = 0; i < books.size(); i++) { //Listeden kitabı bulur ve günceller.
            if (books.get(i).getId().equals(id)) {
                BookDto updatedBook = new BookDto(id,
                        bookDto.getTitle() != null ? bookDto.getTitle() : books.get(i).getTitle(),
                        bookDto.getAuthor() != null ? bookDto.getAuthor() : books.get(i).getAuthor()
                );
                books.set(i, updatedBook);
                return ResponseEntity.ok("Kitap başarıyla güncellendi. ID: " + id);
            }
        }

        return ResponseEntity.notFound().build();
    }

    /*
     Kitap sil endpoint'i:

     - HTTP Method: DELETE
     - URL: /api/books/{id}

     - Örnek: /api/books/1 → ID'si 1 olan kitabı siler
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {

        System.out.println("Kitap siliniyor, ID: " + id);

        boolean removed = books.removeIf(book -> book.getId().equals(id)); // Listeden kitabı bulup ve siler.

        return removed ? ResponseEntity.ok("Kitap başarıyla silindi! ID: " + id)
                : ResponseEntity.notFound().build();
    }
}
