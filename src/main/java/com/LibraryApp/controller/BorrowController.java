package com.LibraryApp.controller;

import com.LibraryApp.dto.BorrowDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/borrow") // Bu controller'ın tüm endpoint'leri /api/borrow ile başlar.
public class BorrowController {

    // Ödünç alma kayıtları - kim hangi kitabı ödünç aldı.
    private final List<BorrowDto> borrows = new ArrayList<>();

    // Constructor'a örnek veriler ekliyoruz.
    public BorrowController() {
        borrows.add(new BorrowDto(1L, 1L, "Java Programming", "John Smith", "Ahmet", "Yılmaz")); // Ahmet (ID:1) Java Programming (ID:1) kitabını ödünç aldı
        borrows.add(new BorrowDto(3L, 2L, "Database Design", "Jane Doe", "Ayşe", "Demir")); // Ayşe (ID:2) Database Design (ID:3) kitabını ödünç aldı
    }

    /*
     Kitap ödünç alma endpoint'i

     - HTTP Method: POST
     - URL: /api/borrow
     - Content-Type: application/json

     Örnek Request Body:
      {
        "bookId": 1,
        "studentId": 2
      }
     */
    @PostMapping
    public ResponseEntity<String> borrowBook(@RequestBody BorrowDto borrowDto) {
        System.out.println("Kitap ödünç alınıyor: " + borrowDto);
        borrows.add(borrowDto); //Ödünç alma kaydını listeye ekle.
        String message = String.format(
                "Kitap başarıyla ödünç alındı!\n" +
                        "Kitap ID: %d\n" +
                        "Öğrenci ID: %d",
                borrowDto.getBookId(), borrowDto.getStudentId()
        );
        return ResponseEntity.ok(message);
    }

    @GetMapping("/me") // Öğrencinin kendi ödünç aldığı kitapları listele endpoint'i HTTP Method: GET URL: /api/borrow/me?studentId=1
    public ResponseEntity<List<BorrowDto>> getMyBorrowedBooks(@RequestParam Long studentId) {
        System.out.println("Öğrencinin ödünç aldığı kitaplar listeleniyor, Öğrenci ID: " + studentId);
        List<BorrowDto> studentBorrows = borrows.stream() //Öğrencinin ödünç aldığı kitapları filtreler.
                .filter(borrow -> borrow.getStudentId().equals(studentId))
                .toList();
        System.out.println("Ödünç alınan kitap sayısı: " + studentBorrows.size());
        return ResponseEntity.ok(studentBorrows);
    }

    @PostMapping("/{bookId}/return") //Kitap iade etme endpoint'i HTTP Method: POST URL: /api/borrow/{bookId}/return?studentId=1
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @RequestParam Long studentId) {
        System.out.println("Kitap iade ediliyor, Kitap ID: " + bookId + ", Öğrenci ID: " + studentId);
        boolean removed = borrows.removeIf(borrow -> // Ödünç alma kaydını bulur ve siler.
                borrow.getBookId().equals(bookId) && borrow.getStudentId().equals(studentId)
        );
        if (removed) {
            String message = String.format(
                    "Kitap başarıyla iade edildi!\n" +
                            "Kitap ID: %d\n" +
                            "Öğrenci ID: %d",
                    bookId, studentId
            );
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body("Bu öğrenci bu kitabı ödünç almamış.");
        }
    }
    public List<BorrowDto> getAllBorrows() { //AdminController için tüm ödünç kayıtlarını döndüren metod oluşturuyoruz.
        return new ArrayList<>(borrows);
    }
}
