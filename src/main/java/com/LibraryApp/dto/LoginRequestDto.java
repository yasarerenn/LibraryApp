package com.LibraryApp.dto;

// Login request verilerini temsil eden DTO sınıfı. Kullanıcının login olmak için gönderdiği bilgileri json formatında almak için.
public class LoginRequestDto {

    private String email;      // Kullanıcı email
    private String password;   // Kullanıcı şifre

    // Default constructor
    public LoginRequestDto() {}

    // Parametreli constructor
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter ve Setter metodları
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @Override
    public String toString() { //toString ile bilgileri yazdırabiliyoruz.
        return "LoginRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
