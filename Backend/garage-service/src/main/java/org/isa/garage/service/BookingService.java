package org.isa.garage.service;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.isa.garage.dto.BookingDTO;
import org.isa.garage.entity.Booking;
import org.isa.garage.entity.BookingStatus;
import org.isa.garage.entity.Schedule;
import org.isa.garage.exception.BookingException;
import org.isa.garage.repository.BookingRepository;
import org.isa.garage.repository.ScheduleRepository;
import org.isa.garage.util.MappingUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ScheduleRepository scheduleRepository;
    private final KafkaProducerService kafkaProducerService;

    public BookingService(BookingRepository bookingRepository, ScheduleRepository scheduleRepository, KafkaProducerService kafkaProducerService){
        this.bookingRepository = bookingRepository;
        this.scheduleRepository = scheduleRepository;
        this.kafkaProducerService = kafkaProducerService;
    }



    public void createBooking(BookingDTO bookingDTO){
        Schedule schedule = scheduleRepository.findById(bookingDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found"));

        // Create a new Booking
        Booking booking = new Booking();
        booking.setCustomerName(bookingDTO.getCustomerName());
        booking.setVehicleType(bookingDTO.getVehicleType());
        booking.setSchedule(schedule);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setBookingTime(bookingDTO.getBookingTime());
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
    }


    public List<BookingDTO> getBookingsByScheduleID(Integer scheduleId){
        List<Booking> bookings = bookingRepository.findAllByScheduleId(scheduleId);
        return MappingUtils.mapBookingsToDTOs(bookings);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<BookingDTO> setBookingStatus(BookingStatus status, Integer id,Integer scheduleId){
        Booking booking =  bookingRepository.findById(id).orElseThrow(()->new BookingException("Booking can not be found!"));
        booking.setStatus(status);
        booking = bookingRepository.save(booking);
        kafkaProducerService.sendBookingStatus(MappingUtils.mapBookingToDTO(booking));
        return getBookingsByScheduleID(scheduleId);
    }

    public Long getCount(){
        return bookingRepository.count();
    }
}
