package com.example.hotel.service;

import java.util.List;

import com.example.hotel.entity.Room;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository repo;

    public List<Room> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public List<Room> listAvailableRooms() {
        return repo.findByAvailability(true);
    }

    public List<Object[]> getRoomTypeHistogramData() {
        return repo.getRoomTypeHistogramData();
    }


    public void save(Room room) {
        if (room.getRoomtype() != null) {
            switch (room.getRoomtype()) {
                case "Suit":
                    room.setPricepernight(2500);
                    break;
                case "Junior Suit":
                    room.setPricepernight(2000);
                    break;
                case "Superior":
                    room.setPricepernight(1500);
                    break;
                case "Standart":
                    room.setPricepernight(1000);
                    break;
            }
        }
        repo.save(room);
    }

    public Room get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public int countTotalRooms() {
        return repo.countTotalRooms();
    }

    public int countAvailableRooms() {
        return repo.countAvailableRooms();
    }

}