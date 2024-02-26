package org.isa.garage.util;

import org.isa.garage.dto.*;
import org.isa.garage.entity.Booking;
import org.isa.garage.entity.GarageService;
import org.isa.garage.entity.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {


    public static GarageServiceDTO mapServiceToDTO(GarageService service) {
        return new GarageServiceDTO(service.getId(), service.getServiceName(), service.getDuration());
    }

    public static ScheduleDTO mapScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setStartTime(schedule.getStartTime());
        scheduleDTO.setEndTime(schedule.getEndTime());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setMaxCapacity(schedule.getMaxCapacity());
        scheduleDTO.setCurrentCapacity(schedule.getCurrentCapacity());

        List<GarageServiceDTO> services = schedule.getGarageServices().stream().map(MappingUtils::mapServiceToDTO).toList();
        scheduleDTO.setGarageServices(services);

        return scheduleDTO;
    }

    public static Schedule mapMultiServiceScheduleDTOToSchedule(MultiServiceScheduleCreateDTO multiServiceScheduleCreateDTO, List<GarageService> services) {
        Schedule schedule = new Schedule();
        schedule.setStartTime(multiServiceScheduleCreateDTO.getStartTime());
        schedule.setEndTime(multiServiceScheduleCreateDTO.getEndTime());
        schedule.setDate(multiServiceScheduleCreateDTO.getDate());
        schedule.setGarageServices(services);
        schedule.setMaxCapacity(multiServiceScheduleCreateDTO.getMaxCapacity());
        schedule.setCurrentCapacity(0);
        return schedule;
    }

    public static Schedule mapScheduleEditDTOToSchedule(ScheduleEditDTO scheduleEditDTO, List<GarageService> services) {
        return new Schedule(
                scheduleEditDTO.getId(),
                scheduleEditDTO.getStartTime(),
                scheduleEditDTO.getEndTime(),
                scheduleEditDTO.getDate(),
                scheduleEditDTO.getMaxCapacity(), 0,
                services
        );
    }

    public static BookingDTO mapBookingToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setBookingId(booking.getBookingId());
        bookingDTO.setCustomerName(booking.getCustomerName());
        bookingDTO.setVehicleType(booking.getVehicleType());
        bookingDTO.setScheduleId(booking.getSchedule().getId());
        bookingDTO.setBookingDate(booking.getBookingDate());
        bookingDTO.setBookingTime(booking.getBookingTime());
        bookingDTO.setStatus(booking.getStatus());

        return bookingDTO;
    }

    public static List<BookingDTO> mapBookingsToDTOs(List<Booking> bookings){
        return bookings.stream().map(MappingUtils::mapBookingToDTO).collect(Collectors.toList());
    }
}
