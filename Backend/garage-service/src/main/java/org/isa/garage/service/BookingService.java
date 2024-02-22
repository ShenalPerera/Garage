package org.isa.garage.service;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.isa.garage.dto.BookingCreateDTO;
import org.isa.garage.entity.Booking;
import org.isa.garage.entity.BookingStatus;
import org.isa.garage.entity.Schedule;
import org.isa.garage.repository.BookingRepository;
import org.isa.garage.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;

    public BookingService(BookingRepository bookingRepository, ScheduleRepository scheduleRepository){
        this.bookingRepository = bookingRepository;
        this.scheduleRepository = scheduleRepository;
    }



    public void createBooking(BookingCreateDTO bookingCreateDTO){
        Schedule schedule = scheduleRepository.findById(bookingCreateDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        // Create a new Booking
        Booking booking = new Booking();
        booking.setCustomerName(bookingCreateDTO.getCustomerName());
        booking.setVehicleType(bookingCreateDTO.getVehicleType());
        booking.setSchedule(schedule);
        booking.setBookingDate(bookingCreateDTO.getBookingDate());
        booking.setBookingTime(bookingCreateDTO.getBookingTime());
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
    }

}
