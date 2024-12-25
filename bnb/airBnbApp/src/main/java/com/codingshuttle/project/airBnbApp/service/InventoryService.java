package com.codingshuttle.project.airBnbApp.service;

import com.codingshuttle.project.airBnbApp.dto.HotelDto;
import com.codingshuttle.project.airBnbApp.dto.HotelSearchRequest;
import com.codingshuttle.project.airBnbApp.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
