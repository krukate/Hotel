package com.example.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestid; // BIGINT в базе данных

    @NotBlank(message = "Вы пропустили это поле")
    private String firstname;

    @NotBlank(message = "Вы пропустили это поле")
    private String lastname;

    @Email(message = "Неверный формат email")
    @NotBlank(message = "Вы пропустили это поле")
    private String email;

    @Pattern(regexp = "^(\\+[0-9]{11}|8[0-9]{10})$", message = "Неправильно заполнен номер")
    @NotBlank(message = "Вы пропустили это поле")
    private String phonenumber;

    @NotNull(message = "Вы пропустили это поле")
    private LocalDate checkindate;

    @NotNull(message = "Вы пропустили это поле")
    private LocalDate checkoutdate;

    @ManyToOne
    @JoinColumn(name = "roomid")
    private Room room;

    public Long getGuestid() {
        return guestid;
    }

    public void setGuestid(Long guestid) {
        this.guestid = guestid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public LocalDate getCheckindate() {
        return checkindate;
    }

    public void setCheckindate(LocalDate checkindate) {
        this.checkindate = checkindate;
    }

    public LocalDate getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(LocalDate checkoutdate) {
        this.checkoutdate = checkoutdate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}