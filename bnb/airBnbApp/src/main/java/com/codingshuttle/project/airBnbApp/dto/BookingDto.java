package com.codingshuttle.project.airBnbApp.dto;

import com.codingshuttle.project.airBnbApp.entity.Guest;
import com.codingshuttle.project.airBnbApp.entity.Hotel;
import com.codingshuttle.project.airBnbApp.entity.Room;
import com.codingshuttle.project.airBnbApp.entity.User;
import com.codingshuttle.project.airBnbApp.entity.enums.BookingStatus;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class BookingDto {

    private Long id;
    private Hotel hotel;
    private Room room;
    private User user;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
