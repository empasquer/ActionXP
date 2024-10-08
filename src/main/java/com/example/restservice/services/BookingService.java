package com.example.restservice.services;

import com.example.restservice.models.Booking;
import com.example.restservice.repositories.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByDate(LocalDate date) {
        return bookingRepository.findByDate(date);
    }


    public void calculatePrice(Booking booking) {

    }

}