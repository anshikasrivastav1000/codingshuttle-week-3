package com.codingshuttle.project.airBnbApp.repository;

import com.codingshuttle.project.airBnbApp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking ,Long> {
}
