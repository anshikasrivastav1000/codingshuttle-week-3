package com.codingshuttle.project.airBnbApp.service;
import com.codingshuttle.project.airBnbApp.dto.HotelDto;
import com.codingshuttle.project.airBnbApp.dto.HotelInfoDto;
import com.codingshuttle.project.airBnbApp.dto.RoomDto;
import com.codingshuttle.project.airBnbApp.entity.Hotel;
import com.codingshuttle.project.airBnbApp.entity.Room;
import com.codingshuttle.project.airBnbApp.exception.ResourceNotFoundException;
import com.codingshuttle.project.airBnbApp.repository.HotelRepository;
import com.codingshuttle.project.airBnbApp.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service

public class HotelServiceImp implements HotelService{
    private static final Logger log = LoggerFactory.getLogger(HotelServiceImp.class);


    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private  final RoomRepository roomRepository;
    public HotelServiceImp(HotelRepository hotelRepository, ModelMapper modelMapper, InventoryService inventoryService, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.inventoryService = inventoryService;
        this.roomRepository = roomRepository;
    }
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
      hotel =  hotelRepository.save(hotel);
      return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));

        //todo delete the future inventories for this hotel
        for(Room room:hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);


    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating  the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
       hotel.setActive(true);
       //TODO : CREATE INVENTORY FOR ALL THE ROOMS FOR THE HOTEL
        //assuming only do it once
        for(Room room:hotel.getRooms()){
            inventoryService.initializeRoomForYear(room);
        }

    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        List<RoomDto> rooms= hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element,RoomDto.class))
                .toList();
        return  new  HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }
}
