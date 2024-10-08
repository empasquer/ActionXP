package com.example.restservice.controllers;

import com.example.restservice.models.Booking;
import com.example.restservice.models.Employee;
import com.example.restservice.services.BookingService;
import com.example.restservice.services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
// @CrossOrigin(origins = "http://localhost:5173")
public class BookingController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    // TESTING
    @PostMapping("/create-booking/hardcoded")
    public ResponseEntity<?> createHardcodedbooking(@RequestBody Booking booking) {
        Optional<Employee> optionalEmployee = employeeService.findById(1l);
        Employee employee = optionalEmployee.orElse(null);
        booking.setEmployee(employee); // Set the employee reference
        bookingService.createBooking(booking);
        return ResponseEntity.ok("Booking registered successfully");
    }

    @GetMapping("/day")
    public ResponseEntity<List<Booking>> getBookingsByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date); // Convert the incoming string to LocalDate
        List<Booking> bookings = bookingService.getBookingsByDate(localDate);
        return ResponseEntity.ok(bookings);
    }

    // save this for later .
    // @PostMapping
    // public Booking createBooking(@RequestBody Booking booking) {
    // Employee employee = employeeService.findById(booking.getEmployeeId())
    // .orElseThrow(() -> new RuntimeException("Employee not found")); // Ensure the
    // employee exists
    // booking.setEmployee(employee); // Set the employee reference
    // return bookingService.createBooking(booking);
    // }
}
