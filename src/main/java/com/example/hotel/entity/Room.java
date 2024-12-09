package com.example.hotel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomid;

    @NotNull(message = "Номер комнаты обязателен")
    private int roomnumber;

    @NotBlank(message = "Тип комнаты обязателен")
    private String roomtype;

    @NotNull(message = "Цена за ночь обязательна")
    private double pricepernight;

    @NotNull(message = "Статус доступности обязателен")
    private boolean availability;

    // Getters and Setters
    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(int roomnumber) {
        this.roomnumber = roomnumber;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public double getPricepernight() {
        return pricepernight;
    }

    public void setPricepernight(double pricepernight) {
        this.pricepernight = pricepernight;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}