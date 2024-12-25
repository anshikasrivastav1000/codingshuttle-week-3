package com.codingshuttle.project.airBnbApp.controller;


import com.codingshuttle.project.airBnbApp.dto.HotelDto;
import com.codingshuttle.project.airBnbApp.dto.HotelInfoDto;
import com.codingshuttle.project.airBnbApp.dto.HotelSearchRequest;
import com.codingshuttle.project.airBnbApp.service.HotelService;
import com.codingshuttle.project.airBnbApp.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")

public class HotelBrowseController {
      private final InventoryService inventoryService;
     private final HotelService hotelService;
    public HotelBrowseController(InventoryService inventoryService, HotelService hotelService) {
        this.inventoryService = inventoryService;
        this.hotelService = hotelService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
    Page<HotelDto> page = inventoryService.searchHotels(hotelSearchRequest);
    return ResponseEntity.ok(page);

    }
    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}

