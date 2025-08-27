package com.LibraryApp.dto;

// Öğrenci verilerini temsil eden DTO (Data Transfer Object) sınıfı. JSON formatında veri alışverişi yapar.
public class StudentDto {

    private Long id;           // Öğrenci ID
    private String firstName;  // Öğrenci adı
    private String lastName;   // Öğrenci soyadı
    private String studentNumber; // Öğrenci numarası
    private String email;      // Öğrenci email'i
    private String password;   // Öğrenci şifresi
    private boolean role; // Kullanıcı-admin kontrolü

    // Default constructor
    public StudentDto() {}

    // Parametreli constructor
    public StudentDto(Long id, String firstName, String lastName, String studentNumber, String email, String password, boolean role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getter ve Setter metodları
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getStudentNumber() {return studentNumber;}
    public void setStudentNumber(String studentNumber) {this.studentNumber = studentNumber;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public boolean isRole() {return role;}
    public void setRole(boolean role) {this.role = role;}

    @Override //toString ile bilgileri yazdırabiliyoruz.
    public String toString() {
        return "StudentDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
