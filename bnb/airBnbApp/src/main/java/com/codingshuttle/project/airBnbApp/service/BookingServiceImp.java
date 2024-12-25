package com.codingshuttle.project.airBnbApp.service;


import com.codingshuttle.project.airBnbApp.dto.BookingDto;
import com.codingshuttle.project.airBnbApp.dto.BookingRequest;
import com.codingshuttle.project.airBnbApp.entity.Hotel;
import com.codingshuttle.project.airBnbApp.entity.Inventory;
import com.codingshuttle.project.airBnbApp.entity.Room;
import com.codingshuttle.project.airBnbApp.exception.ResourceNotFoundException;
import com.codingshuttle.project.airBnbApp.repository.BookingRepository;
import com.codingshuttle.project.airBnbApp.repository.HotelRepository;
import com.codingshuttle.project.airBnbApp.repository.InventoryRepository;
import com.codingshuttle.project.airBnbApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService{

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        log.info("INITIALISGING BOOKING for hotel : {},room:{},dat{} - {}" ,bookingRequest.getHotelId(),
                bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());
       Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() ->
               new ResourceNotFoundException("Hotel Not Found With id: "+bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() ->
                new ResourceNotFoundException("Hotel Not fond with id :" +bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(room.getId(),
                bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount());
        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;

        if(inventoryList.size() !=daysCount){
            throw  new IllegalStateException("rOOM IS NOT AVAILABLE ANYMORE");
        }

        //reserve the room / update the booked count of inventories

        for(Inventory inventory : inventoryList){
            inventory.setBookedCount(inventory.getBookedCount() + bookingRequest.getRoomsCount());
        }

    };
}
