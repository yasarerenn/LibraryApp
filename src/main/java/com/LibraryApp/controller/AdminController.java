package com.LibraryApp.controller;

import com.LibraryApp.dto.BorrowDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin") //Bu controller'ın tüm endpoint'leri /api/admin ile başlar
public class AdminController {
    //BorrowController'a referans
    private final BorrowController borrowController;
    private final BookController bookController;
    private final StudentController studentController;

    // Constructor injection
    public AdminController(BorrowController borrowController, BookController bookController, StudentController studentController) {
        this.borrowController = borrowController;
        this.bookController = bookController;
        this.studentController = studentController;
    }

    /*
     Tüm ödünç hareketlerini listele endpointi:

     - HTTP Method: GET
     - URL: /api/admin/borrows

     */
    @GetMapping("/borrows")
    public ResponseEntity<List<BorrowDto>> getAllBorrows() {
        System.out.println("Admin: Tüm ödünç hareketleri listeleniyor...");
        List<BorrowDto> allBorrows = borrowController.getAllBorrows(); //BorrowController'dan tüm ödünç kayıtlarını al.

        List<BorrowDto> enrichedBorrows = allBorrows.stream() //Her ödünç kaydı için kitap ve öğrenci bilgilerini ekler.
                .map(borrow -> {
                    var bookResponse = bookController.getBookById(borrow.getBookId());
                    String bookTitle = "Bilinmeyen Kitap";
                    String bookAuthor = "Bilinmeyen Yazar";

                    if (bookResponse.getStatusCode().is2xxSuccessful() && bookResponse.getBody() != null) {
                        bookTitle = bookResponse.getBody().getTitle();
                        bookAuthor = bookResponse.getBody().getAuthor();
                    }

                    var studentResponse = studentController.getStudentById(borrow.getStudentId()); //Id ye göre belirli bir öğrenci bilgilerini alıyoruz.
                    String studentName = "Bilinmeyen Öğrenci"; //Id'de öğrenci bulunmadığında dönecek veriler.
                    String studentSurname = "";

                    return new BorrowDto( //Öğrenci bilgilerini dönüyoruz.
                            borrow.getBookId(),
                            borrow.getStudentId(),
                            bookTitle,
                            bookAuthor,
                            studentName,
                            studentSurname
                    );
                })
                .toList();

        System.out.println("Toplam ödünç kaydı sayısı: " + enrichedBorrows.size());

        return ResponseEntity.ok(enrichedBorrows);
    }

    /*
     Sistem istatistiklerini getir endpointi:

     - HTTP Method: GET
     - URL: /api/admin/stats

     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        System.out.println("Admin: Sistem istatistikleri hesaplanıyor.");

        Map<String, Object> stats = new HashMap<>();

        //Verileri alıyoruz.
        List<BorrowDto> allBorrows = borrowController.getAllBorrows();
        var booksResponse = bookController.getAllBooks("", "");
        var studentsResponse = studentController.getAllStudents();

        //Kitap sayısı
        int totalBooks = 0;
        if (booksResponse.getStatusCode().is2xxSuccessful() && booksResponse.getBody() != null) {
            totalBooks = booksResponse.getBody().size();
        }

        //Öğrenci sayısı
        int totalStudents = 0;
        if (studentsResponse.getStatusCode().is2xxSuccessful() && studentsResponse.getBody() != null) {
            totalStudents = studentsResponse.getBody().size();
        }

        //Ödünç alınan kitap sayısı
        int borrowedBooks = allBorrows.size();

        // İstatistikler
        stats.put("totalBooks", totalBooks); //Toplam kitap sayısı
        stats.put("totalStudents", totalStudents); //toplam öğrenci sayısı
        stats.put("borrowedBooks", borrowedBooks); //Ödünçte olan kitap sayısı

        System.out.println("İstatistikler hazırlandı:");
        System.out.println("Toplam Kitap Sayısı: " + totalBooks);
        System.out.println("Toplam Öğrenci Sayısı: " + totalStudents);
        System.out.println("Ödünçte Olan Kitap Sayısı: " + borrowedBooks);

        return ResponseEntity.ok(stats); //Bu satır ile HTTP 200 OK status code'u döner, JSON formatında stats verisini döner, Content-Type: application/json header'ı ekler, Client'a response'u gönderir.
    }
}
