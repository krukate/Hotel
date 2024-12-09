package com.example.hotel.repository;

import java.util.List;

import com.example.hotel.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("SELECT g FROM Guest g WHERE " +
            "(CONCAT(g.firstname, ' ', g.lastname) LIKE %?1% OR CAST(g.room.roomnumber AS string) LIKE %?1%) AND " +
            "(CAST(g.checkindate AS string) LIKE %?2% OR CAST(g.checkoutdate AS string) LIKE %?2%)")
    List<Guest> search(String keyword, String date);

    @Query("SELECT COUNT(g) FROM Guest g WHERE g.checkoutdate >= CURRENT_DATE")
    int countActiveBookings();

    @Query("SELECT g.checkindate, COUNT(g) FROM Guest g GROUP BY g.checkindate ORDER BY g.checkindate")
    List<Object[]> getArrivalHistogramData();
}
