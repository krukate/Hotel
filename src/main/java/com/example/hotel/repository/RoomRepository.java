package com.example.hotel.repository;

import java.util.List;

import com.example.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE CONCAT(r.roomnumber, ' ', r.roomtype, ' ', r.pricepernight, ' ', r.availability) LIKE %?1%")
    List<Room> search(String keyword);

    List<Room> findByAvailability(boolean availability);

    List<Room> findByRoomtype(String roomtype);

    @Query("SELECT COUNT(r) FROM Room r")
    int countTotalRooms();

    @Query("SELECT COUNT(r) FROM Room r WHERE r.availability = true")
    int countAvailableRooms();

    @Query("SELECT r.roomtype, COUNT(r) FROM Room r GROUP BY r.roomtype ORDER BY r.roomtype")
    List<Object[]> getRoomTypeHistogramData();
}