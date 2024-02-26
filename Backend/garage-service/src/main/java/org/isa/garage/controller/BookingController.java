package org.isa.garage.controller;

import org.isa.garage.dto.BookingDTO;
import org.isa.garage.entity.BookingStatus;
import org.isa.garage.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {

    public final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @GetMapping("/get-bookings")
    public ResponseEntity<?> getAllBookings(@RequestParam(name = "scheduleId") Integer scheduleId){

        List<BookingDTO> bookingDTOList = bookingService.getBookingsByScheduleID(scheduleId);

        return new ResponseEntity<>(bookingDTOList, HttpStatus.OK);
    }

    @GetMapping("/set-status")
    public ResponseEntity<?> setBookingStatus(@RequestParam(name = "action")BookingStatus status, @RequestParam(name = "bookingId") Integer bookingId, @RequestParam(name = "scheduleId") Integer scheduleId ){
        List<BookingDTO> bookingDTOList = bookingService.setBookingStatus(status,bookingId,scheduleId);
        return new ResponseEntity<>(bookingDTOList,HttpStatus.OK);
    }
}
