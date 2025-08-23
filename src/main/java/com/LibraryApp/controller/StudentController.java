package com.LibraryApp.controller;

import com.LibraryApp.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students") //Bu controller'ın tüm endpoint'leri /api/students ile başlar
public class StudentController {
    private final List<StudentDto> students = new ArrayList<>();

    // Constructor'da verileri ekle
    public StudentController() {
        students.add(new StudentDto(1L, "Ahmet", "Yılmaz", "2023001", "ahmet@example.com", "123456"));
        students.add(new StudentDto(2L, "Ayşe", "Demir", "2023002", "ayse@example.com", "123456"));
        students.add(new StudentDto(3L, "Mehmet", "Kaya", "2023003", "mehmet@example.com", "123456"));
    }

    @GetMapping //Tüm öğrencileri listele endpoint'i /api/students Method: GET
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        System.out.println("Tüm öğrenciler listeleniyor...");
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}") //Tek öğrenci getir endpoint'i Örnek: /api/students/1 Method: GET
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        System.out.println("Öğrenci aranıyor, ID: " + id);
        StudentDto student = students.stream()        //Listeden ID'ye göre öğrenci arar
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}") //Öğrenci bilgilerini güncelle endpoint'i /api/students/{id} HTTP Method: PATCH Content-Type: application/json. Put kullanmadık çünkü put tamamını değiştirir, patch ise belirtilen alanları değiştirir.
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        System.out.println("Öğrenci güncelleniyor, ID: " + id);
        System.out.println("Güncellenecek bilgiler: " + studentDto);
        // Listeden öğrenciyi bul ve güncelle
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                StudentDto updatedStudent = new StudentDto(id,
                        studentDto.getFirstName() != null ? studentDto.getFirstName() : students.get(i).getFirstName(),
                        studentDto.getLastName() != null ? studentDto.getLastName() : students.get(i).getLastName(),
                        studentDto.getStudentNumber() != null ? studentDto.getStudentNumber() : students.get(i).getStudentNumber(),
                        studentDto.getEmail() != null ? studentDto.getEmail() : students.get(i).getEmail(),
                        studentDto.getPassword() != null ? studentDto.getPassword() : students.get(i).getPassword()
                );
                students.set(i, updatedStudent);
                return ResponseEntity.ok("Öğrenci başarıyla güncellendi! ID: " + id);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") //Öğrenci sil endpoint'i /api/students/{id} HTTP Method: DELETE
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        System.out.println("Öğrenci siliniyor, ID: " + id);
        // Listeden öğrenciyi bul ve sil
        boolean removed = students.removeIf(student -> student.getId().equals(id));
        return removed ? ResponseEntity.ok("Öğrenci başarıyla silindi! ID: " + id)
                : ResponseEntity.notFound().build();
    }

    public StudentDto addStudent(StudentDto studentDto) { //AuthController için yeni öğrenci ekleme metodu tanımlıyoruz.
        studentDto.setId((long) (students.size() + 1));
        students.add(studentDto);
        return studentDto;
    }
}
