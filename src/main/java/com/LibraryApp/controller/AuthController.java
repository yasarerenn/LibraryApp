package com.LibraryApp.controller;

import com.LibraryApp.dto.LoginRequestDto;
import com.LibraryApp.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth") //Bu controller'ın tüm endpoint'leri /api/auth ile başlar
public class AuthController {

    private final StudentController studentController; //StudentController'a referans.

    public AuthController(StudentController studentController) { //Constructor injection
        this.studentController = studentController;
    }

    /*
    Yeni öğrenci kaydı endpoint'i:

     - HTTP Method: POST
     - URL: /api/auth/register
     - Content-Type: application/json

     Örnek:
      {
        "firstName": "Yaşar",
        "lastName": "Eren",
        "studentNumber": "20252025",
        "email": "yasarerenn@outlook.com.tr",
        "password": "123456789"
     * }
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody StudentDto studentDto) {
        StudentDto savedStudent = studentController.addStudent(studentDto); //StudentController'ın addStudent metodunu kullanıyoruz.
        System.out.println("Öğrenci kaydedildi: " + savedStudent);
        return ResponseEntity.ok("Öğrenci başarıyla kaydedildi! ID: " + savedStudent.getId());
    }

    /*
    Kullanıcı girişi endpoint'i:

     - HTTP Method: POST
     - URL: /api/auth/login
     - Content-Type: application/json

     Örnek:
      {
        "email": "ahmet@example.com",
        "password": "123456"
      }
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequest) {
        System.out.println("Giriş denemesi yapıldı. E-posta: " + loginRequest.getEmail() + ", Şifre: " + loginRequest.getPassword());

        List<StudentDto> students = studentController.getAllStudents().getBody(); //StudentController'dan öğrenci listesini alıyoruz ve email/password kontrolü yapıyoruz.

        if (students != null) { //Email ve password eşleşen öğrenciyi buluyoruz.
            StudentDto foundStudent = students.stream()
                    .filter(student -> student.getEmail().equals(loginRequest.getEmail()) &&
                            student.getPassword().equals(loginRequest.getPassword()))
                    .findFirst()
                    .orElse(null);

            if (foundStudent != null) { //Giriş başarılı
                System.out.println("Giriş başarılı: " + foundStudent.getFirstName() + " " + foundStudent.getLastName());
                return ResponseEntity.ok("Giriş başarılı!");
            } else { //Giriş başarısız
                System.out.println("Giriş başarısız: Email veya şifre yanlış");
                return ResponseEntity.badRequest().body("Giriş başarısız! Email veya şifre yanlış.");
            }
        } else {
            System.out.println("Öğrenci listesi bulunamadı");
            return ResponseEntity.badRequest().body("Sistem hatası! Öğrenci listesi bulunamadı.");
        }
    }
}
