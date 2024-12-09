package com.example.hotel.service;

import java.util.List;

import com.example.hotel.entity.Guest;
import com.example.hotel.entity.Room;
import com.example.hotel.repository.GuestRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepo;

    @Autowired
    private RoomRepository roomRepo;

    public List<Guest> listAll(String keyword, String date) {
        if (keyword != null && date != null) {
            return guestRepo.search(keyword, date);
        }
        return guestRepo.findAll();
    }

    public List<Object[]> getArrivalHistogramData() {
        return guestRepo.getArrivalHistogramData();
    }

    public void save(Guest guest) {
        Room room = roomRepo.findById(guest.getRoom().getRoomid()).orElse(null);
        if (room != null) {
            room.setAvailability(false);
            roomRepo.save(room);
        }
        guestRepo.save(guest);
    }

    public Guest get(Long id) {
        return guestRepo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        Guest guest = guestRepo.findById(id).orElse(null);
        if (guest != null) {
            Room room = roomRepo.findById(guest.getRoom().getRoomid()).orElse(null);
            if (room != null) {
                room.setAvailability(true);
                roomRepo.save(room);
            }
            guestRepo.deleteById(id);
        }
    }

    public int countActiveBookings() {
        return guestRepo.countActiveBookings();
    }
}