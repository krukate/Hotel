package com.example.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.hotel.entity.Guest;
import com.example.hotel.entity.Room;
import com.example.hotel.service.GuestService;
import com.example.hotel.service.RoomService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ApiController {

    @Autowired
    private GuestService guestService;

    @Autowired
    private RoomService roomService;

    @PostMapping(value = "/guests", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<?> saveGuest(@Valid @ModelAttribute Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        guestService.save(guest);
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @GetMapping("/guests/{id}")
    public ResponseEntity<Guest> getGuest(@PathVariable Long id) {
        Guest guest = guestService.get(id);
        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @PutMapping("/guests/{id}")
    public ResponseEntity<?> updateGuest(@PathVariable Long id, @Valid @RequestBody Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Guest existingGuest = guestService.get(id);
        if (existingGuest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingGuest.setFirstname(guest.getFirstname());
        existingGuest.setLastname(guest.getLastname());
        existingGuest.setEmail(guest.getEmail());
        existingGuest.setPhonenumber(guest.getPhonenumber());
        existingGuest.setCheckindate(guest.getCheckindate());
        existingGuest.setCheckoutdate(guest.getCheckoutdate());
        existingGuest.setRoom(guest.getRoom());
        guestService.save(existingGuest);
        return new ResponseEntity<>(existingGuest, HttpStatus.OK);
    }

    @PatchMapping("/guests/{id}")
    public ResponseEntity<?> patchGuest(@PathVariable Long id, @Valid @RequestBody Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        Guest existingGuest = guestService.get(id);
        if (existingGuest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (guest.getFirstname() != null) {
            existingGuest.setFirstname(guest.getFirstname());
        }
        if (guest.getLastname() != null) {
            existingGuest.setLastname(guest.getLastname());
        }
        if (guest.getEmail() != null) {
            existingGuest.setEmail(guest.getEmail());
        }
        if (guest.getPhonenumber() != null) {
            existingGuest.setPhonenumber(guest.getPhonenumber());
        }
        if (guest.getCheckindate() != null) {
            existingGuest.setCheckindate(guest.getCheckindate());
        }
        if (guest.getCheckoutdate() != null) {
            existingGuest.setCheckoutdate(guest.getCheckoutdate());
        }
        if (guest.getRoom() != null) {
            existingGuest.setRoom(guest.getRoom());
        }
        guestService.save(existingGuest);
        return new ResponseEntity<>(existingGuest, HttpStatus.OK);
    }

    @DeleteMapping("/guests/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/rooms", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Room> saveRoom(Room room) {
        if (room.getRoomnumber() == 0 || room.getRoomtype() == null ||
                room.getPricepernight() == 0 || room.isAvailability() == false) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        roomService.save(room);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        Room room = roomService.get(id);
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PatchMapping("/rooms/{id}")
    public ResponseEntity<Room> patchRoom(@PathVariable Long id, @RequestBody Room room) {
        Room existingRoom = roomService.get(id);
        if (existingRoom == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (room.getRoomnumber() != 0) {
            existingRoom.setRoomnumber(room.getRoomnumber());
        }
        if (room.getRoomtype() != null) {
            existingRoom.setRoomtype(room.getRoomtype());
        }
        if (room.getPricepernight() != 0) {
            existingRoom.setPricepernight(room.getPricepernight());
        }
        if (room.isAvailability() != false) {
            existingRoom.setAvailability(room.isAvailability());
        }
        roomService.save(existingRoom);
        return new ResponseEntity<>(existingRoom, HttpStatus.OK);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}