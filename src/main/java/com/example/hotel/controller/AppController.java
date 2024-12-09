package com.example.hotel.controller;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.example.hotel.entity.Guest;
import com.example.hotel.entity.Room;
import com.example.hotel.service.GuestService;
import com.example.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

@Controller
public class AppController {

    @Autowired
    private GuestService guestService;

    @Autowired
    private RoomService roomService;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    @RequestMapping("/guests")
    public String viewGuestsPage(Model model, @Param("keyword") String keyword, @Param("date") String date, @Param("roomNumber") String roomNumber, @Param("sortOrder") String sortOrder) {
        List<Guest> listGuests;

        // Если параметры отсутствуют, загружается полный список
        if ((keyword == null || keyword.isEmpty()) && (date == null || date.isEmpty()) && (roomNumber == null || roomNumber.isEmpty())) {
            listGuests = guestService.listAll(null, null);
        } else {
            listGuests = guestService.listAll(keyword, date);
        }

        // Сортировка по дате
        if (sortOrder != null) {
            if (sortOrder.equals("asc")) {
                listGuests.sort(Comparator.comparing(Guest::getCheckindate));
            } else if (sortOrder.equals("desc")) {
                listGuests.sort(Comparator.comparing(Guest::getCheckindate).reversed());
            }
        }

        model.addAttribute("listGuests", listGuests);
        model.addAttribute("keyword", keyword);
        model.addAttribute("date", date);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("sortOrder", sortOrder); // Передаем текущий порядок сортировки
        return "guests";
    }

    @RequestMapping("/rooms")
    public String viewRoomsPage(Model model,
                                @Param("keyword") String keyword,
                                @Param("sortOrder") String sortOrder,
                                @Param("filterType") String filterType) {
        List<Room> listRooms;
        if (keyword != null && !keyword.isEmpty()) {
            listRooms = roomService.listAll(keyword);
        } else {
            listRooms = roomService.listAll(null);
        }

        if (filterType != null && !filterType.isEmpty()) {
            listRooms = listRooms.stream()
                    .filter(room -> room.getRoomtype().equals(filterType))
                    .collect(Collectors.toList());
        }

        if (sortOrder != null) {
            if (sortOrder.equals("asc")) {
                listRooms.sort(Comparator.comparingDouble(Room::getPricepernight));
            } else if (sortOrder.equals("desc")) {
                listRooms.sort(Comparator.comparingDouble(Room::getPricepernight).reversed());
            }
        }

        model.addAttribute("listRooms", listRooms);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortOrder", sortOrder); // Передаем текущий порядок сортировки
        model.addAttribute("filterType", filterType);
        return "rooms";
    }

    @RequestMapping("/new_guest")
    @PreAuthorize("hasRole('ADMIN')")
    public String showNewGuestForm(Model model) {
        Guest guest = new Guest();
        List<Room> rooms = roomService.listAvailableRooms();
        List<String> countryCodes = List.of("+1", "+44", "+7", "+86", "+91"); // Список кодов
        model.addAttribute("guest", guest);
        model.addAttribute("rooms", rooms);
        model.addAttribute("countryCodes", countryCodes);
        return "new_guest";
    }

    @RequestMapping(value = "/save_guest", method = RequestMethod.POST)
    public String saveGuest(@Valid @ModelAttribute("guest") Guest guest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Room> rooms = roomService.listAvailableRooms();
            model.addAttribute("rooms", rooms);
            return "new_guest";
        }
        try {
            guestService.save(guest);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении гостя: " + e.getMessage());
        }
        return "redirect:/guests";
    }

    @RequestMapping("/edit_guest/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showEditGuestForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_guest");
        Guest guest = guestService.get(id);
        if (guest == null) {
            throw new IllegalArgumentException("Гость с ID " + id + " не найден");
        }
        List<Room> rooms = roomService.listAvailableRooms();
        mav.addObject("guest", guest);
        mav.addObject("rooms", rooms);
        return mav;
    }

    @RequestMapping("/delete_guest/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteGuest(@PathVariable(name = "id") Long id) {
        try {
            guestService.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении гостя: " + e.getMessage());
        }
        return "redirect:/guests";
    }

    @RequestMapping("/new_room")
    @PreAuthorize("hasRole('ADMIN')")
    public String showNewRoomForm(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "new_room";
    }

    @RequestMapping(value = "/save_room", method = RequestMethod.POST)
    public String saveRoom(@Valid @ModelAttribute("room") Room room, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new_room";
        }
        try {
            roomService.save(room);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при сохранении номера: " + e.getMessage());
        }
        return "redirect:/rooms";
    }

    @RequestMapping("/edit_room/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showEditRoomForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_room");
        Room room = roomService.get(id);
        if (room == null) {
            throw new IllegalArgumentException("Номер с ID " + id + " не найден");
        }
        mav.addObject("room", room);
        return mav;
    }

    @RequestMapping("/delete_room/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoom(@PathVariable(name = "id") Long id) {
        try {
            roomService.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении номера: " + e.getMessage());
        }
        return "redirect:/rooms";
    }

    @RequestMapping("/statistics")
    public String viewStatisticsPage(Model model) {
        int activeBookings = guestService.countActiveBookings();
        int totalRooms = roomService.countTotalRooms();
        int availableRooms = roomService.countAvailableRooms();

        model.addAttribute("activeBookings", activeBookings);
        model.addAttribute("totalRooms", totalRooms);
        model.addAttribute("availableRooms", availableRooms);
        return "statistics";
    }

    @RequestMapping("/histogram-arrival")
    public @ResponseBody List<Object[]> getArrivalHistogramData() {
        return guestService.getArrivalHistogramData();
    }

    @RequestMapping("/room-type-histogram-data")
    public @ResponseBody List<Object[]> getRoomTypeHistogramData() {
        return roomService.getRoomTypeHistogramData();
    }

    @RequestMapping("/about_author")
    public String viewAboutAuthorPage() {
        return "about_author";
    }
}