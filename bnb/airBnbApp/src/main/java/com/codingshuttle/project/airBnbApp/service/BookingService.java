package com.codingshuttle.project.airBnbApp.service;

import com.codingshuttle.project.airBnbApp.dto.BookingDto;
import com.codingshuttle.project.airBnbApp.dto.BookingRequest;

public interface BookingService {
    BookingDto initialiseBooking(BookingRequest bookingRequest);
}
