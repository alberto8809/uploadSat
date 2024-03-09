package org.example.satdwn.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String user_name;
    private String user_maternal_lastname;
    private String user_lastname;
    private String user_phone;
    private String user_city;
    private String user_mail;
    private String user_password;

    private String token;
    private String user_type;


    public User() {
    }

    public User(long user_id, String user_name, String user_maternal_lastname, String user_lastname, String user_phone, String user_city, String user_mail, String user_password, String token, String user_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_maternal_lastname = user_maternal_lastname;
        this.user_lastname = user_lastname;
        this.user_phone = user_phone;
        this.user_city = user_city;
        this.user_mail = user_mail;
        this.user_password = user_password;
        this.token = token;
        this.user_type = user_type;
    }


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_maternal_lastname() {
        return user_maternal_lastname;
    }

    public void setUser_maternal_lastname(String user_maternal_lastname) {
        this.user_maternal_lastname = user_maternal_lastname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = new BCryptPasswordEncoder().encode(user_password);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_maternal_lastname='" + user_maternal_lastname + '\'' +
                ", user_lastname='" + user_lastname + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_city='" + user_city + '\'' +
                ", user_mail='" + user_mail + '\'' +
                ", user_password='" + user_password + '\'' +
                ", token='" + token + '\'' +
                ", user_type='" + user_type + '\'' +
                '}';
    }
}
